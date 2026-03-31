package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.smshistory.service.SmsHistoryService;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.LargeScaleAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.sms.event.LargeScaleSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.util.Set;

import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.RESULT_DURATION_ALARM_ZSET;
import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.RESULT_DURATION_SMS_ZSET;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerSmsPayload> smsRedisTemplate;
    private final RedisTemplate<String, SchedulerAlarmPayload> alarmRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final ResultDurationRepository resultDurationRepository;
    private final ClubRepository clubRepository;
    private final SmsHistoryService smsHistoryService;

    @Transactional
    public void execute() {

        ResultDuration resultDuration = resolveExecutableResultDuration();
        if (resultDuration == null) {
            log.info("처리 가능한 발표 기간이 없습니다.");
            return;
        }

        sendSMS(resultDuration);

        sendAlarm(resultDuration);
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    @Transactional
    public void pollAndExecutePendingResultDuration() {
        execute();
    }

    private void sendSMS(ResultDuration resultDuration) {

        long now = Instant.now().getEpochSecond();

        Set<SchedulerSmsPayload> payloads =
                smsRedisTemplate.opsForZSet()
                        .rangeByScore(RESULT_DURATION_SMS_ZSET, 0, now + 5); // 대규모 데이터 처리로 인한 실행 시간 지연 고려 설정

        log.info("SMS 발송 대상 수 = {}", payloads == null ? 0 : payloads.size());

        if (payloads == null || payloads.isEmpty()) {
            return;
        }

        payloads.forEach(payload -> {
            try {
                if (!claimSmsPayload(payload)) {
                    return;
                }
                publishSmsEvent(payload, resultDuration);
            } catch (Exception e) {
                log.error("SMS 이벤트 발행 준비 실패: submissionId={}, smsHistoryId={}",
                        payload.submissionId(), payload.smsHistoryId(), e);
                restoreSmsPayload(payload, resultDuration);
            }
        });
    }

    private void sendAlarm(ResultDuration resultDuration) {

        long now = Instant.now().getEpochSecond();

        Set<SchedulerAlarmPayload> payloads =
                alarmRedisTemplate.opsForZSet()
                        .rangeByScore(RESULT_DURATION_ALARM_ZSET, 0, now + 5); // 대규모 데이터 처리로 인한 실행 시간 지연 고려 설정

        log.info("알람 발송 대상 수 = {}", payloads == null ? 0 : payloads.size());

        if (payloads == null || payloads.isEmpty()) {
            return;
        }

        payloads.forEach(payload -> {
            try {
                if (!claimAlarmPayload(payload)) {
                    return;
                }
                publishAlarmEvent(payload, resultDuration);
            } catch (Exception e) {
                log.error("알람 이벤트 발행 준비 실패: userId={}, submissionId={}",
                        payload.userId(), payload.submissionId(), e);
                restoreAlarmPayload(payload, resultDuration);
            }
        });
    }

    private void publishSmsEvent(SchedulerSmsPayload payload, ResultDuration resultDuration) {
        smsHistoryService.markRequested(payload.smsHistoryId());

        LargeScaleSmsEvent event = LargeScaleSmsEvent.builder()
                .smsHistoryId(payload.smsHistoryId())
                .phoneNumber(payload.phoneNumber())
                .message(payload.isPassed()
                        ? Message.CLUB_FINAL_ACCEPTED
                        : Message.CLUB_FINAL_REJECTED)
                .clubName(payload.clubName())
                .payload(payload)
                .resultDuration(resultDuration)
                .build();

        eventPublisher.publishEvent(event);

        log.info("SMS 이벤트 발행: submissionId={}, phone={}", payload.submissionId(), payload.phoneNumber());
    }

    private void publishAlarmEvent(SchedulerAlarmPayload payload, ResultDuration resultDuration) {
        Club club = clubRepository.findById(payload.clubId()).orElseThrow();

        LargeScaleAlarmEvent event = LargeScaleAlarmEvent.builder()
                .alarmType(payload.alarmType())
                .category(payload.alarmType().getCategory())
                .title(payload.alarmType().formatTitle(club.getClubName()))
                .content(payload.alarmType().formatContent(club.getClubName()))
                .clubId(club.getId())
                .userId(payload.userId())
                .resultDuration(resultDuration)
                .payload(payload)
                .isPassed(payload.isPassed())
                .build();

        eventPublisher.publishEvent(event);

        log.info("알람 이벤트 발행: userId={}, alarmType={}", payload.userId(), payload.alarmType());
    }

    private ResultDuration resolveExecutableResultDuration() {
        return resultDurationRepository.findPendingResultDurationForUpdate()
                .or(() -> resultDurationRepository.findTopByOrderByIdDesc())
                .orElse(null);
    }

    private boolean claimSmsPayload(SchedulerSmsPayload payload) {
        Long removed = smsRedisTemplate.opsForZSet().remove(RESULT_DURATION_SMS_ZSET, payload);
        return removed != null && removed > 0;
    }

    private boolean claimAlarmPayload(SchedulerAlarmPayload payload) {
        Long removed = alarmRedisTemplate.opsForZSet().remove(RESULT_DURATION_ALARM_ZSET, payload);
        return removed != null && removed > 0;
    }

    private void restoreSmsPayload(SchedulerSmsPayload payload, ResultDuration resultDuration) {
        long score = resultDuration.getResultDurationTime().atZone(java.time.ZoneId.of("Asia/Seoul")).toEpochSecond();
        smsRedisTemplate.opsForZSet().add(RESULT_DURATION_SMS_ZSET, payload, score);
    }

    private void restoreAlarmPayload(SchedulerAlarmPayload payload, ResultDuration resultDuration) {
        long score = resultDuration.getResultDurationTime().atZone(java.time.ZoneId.of("Asia/Seoul")).toEpochSecond();
        alarmRedisTemplate.opsForZSet().add(RESULT_DURATION_ALARM_ZSET, payload, score);
    }
}
