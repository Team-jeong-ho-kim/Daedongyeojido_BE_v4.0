package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationAlreadyExecutedException;
import team.jeonghokim.daedongyeojido.domain.smshistory.service.SmsHistoryService;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
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
    private final SubmissionRepository submissionRepository;

    @Transactional
    public void execute() {

        ResultDuration resultDuration = resultDurationRepository.findPendingResultDurationForUpdate()
                .orElseThrow(() -> ResultDurationAlreadyExecutedException.EXCEPTION);

        sendSMS(resultDuration);

        sendAlarm(resultDuration);
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

        payloads.forEach(payload -> publishSmsEvent(payload, resultDuration));
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

        payloads.forEach(payload -> publishAlarmEvent(payload, resultDuration));
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
        submissionRepository.findByUserIdAndClubId(payload.userId(), payload.clubId())
                .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION)
                .applyUserPassResult(payload.isPassed());

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
}
