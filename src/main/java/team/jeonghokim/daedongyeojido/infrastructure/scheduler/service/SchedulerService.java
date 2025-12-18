package team.jeonghokim.daedongyeojido.infrastructure.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;
    private final SmsService smsService;
    private final ResultDurationRepository resultDurationRepository;

    public static final String RESULT_DURATION_ZSET = "club:result-duration";
    public static final String SEOUL_TIME_ZONE = "Asia/Seoul";

    @Scheduled(fixedRate = 10_000)
    @SchedulerLock(name = "resultDurationExecute", lockAtMostFor = "9s", lockAtLeastFor = "1s")
    public void execute() {

        // 어드민 발표기간 설정 전 스케줄러 대기 상태
        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElse(null);

        if (resultDuration == null) {
            return;
        }

        if (!isResultDuration(resultDuration)) {
            return;
        }

        Set<SchedulerPayload> messages = queryMessages();
        if (messages.isEmpty()) {
            return;
        }

        sendSms(messages);
    }

    private boolean isResultDuration(ResultDuration resultDuration) {
        if (resultDuration == null) {
            return false;
        }

        long now = Instant.now().getEpochSecond();
        long duration = resultDuration.getResultDuration()
                .atZone(ZoneId.of(SEOUL_TIME_ZONE))
                .toEpochSecond();

        return now >= duration;
    }

    private Set<SchedulerPayload> queryMessages() {
        long now = Instant.now().getEpochSecond();

        return smsRedisTemplate.opsForZSet()
                .rangeByScore(RESULT_DURATION_ZSET, 0, now);
    }

    private void sendSms(Set<SchedulerPayload> messages) {
        for (SchedulerPayload payload : messages) {
            try {
                smsService.send(
                        payload.phoneNumber(),
                        payload.isPassed()
                                ? Message.CLUB_FINAL_ACCEPTED
                                : Message.CLUB_FINAL_REJECTED
                );

                smsRedisTemplate.opsForZSet()
                        .remove(RESULT_DURATION_ZSET, payload);

            } catch (Exception e) {
                log.error(
                        "SMS 문자 전송에 실패함 phoneNumber={}, submissionId={}, 다음 스케줄링에서 재시도",
                        payload.phoneNumber(),
                        payload.submissionId(),
                        e
                );
            }
        }
    }

}
