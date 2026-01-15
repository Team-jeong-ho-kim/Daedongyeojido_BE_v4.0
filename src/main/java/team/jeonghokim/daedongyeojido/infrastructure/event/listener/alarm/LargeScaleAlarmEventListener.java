package team.jeonghokim.daedongyeojido.infrastructure.event.listener.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.LargeScaleAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.HttpApiException;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.SmsEventFinalFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class LargeScaleAlarmEventListener {

    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, SchedulerAlarmPayload> smsRedisTemplate;
    private final DecideResultDurationService decideResultDurationService;

    private static final String LARGE_SCALE_ALARM_EVENT_RETRY = "recoverLargeScaleAlarmEvent";
    private static final String RESULT_DURATION_ALARM_ZSET = "club:result-duration-alarm";
    private static final String FAILED_ZSET  = "user:result-duration:failed";

    @Async("largeScaleAlarmExecutor")
    @Retryable(
            retryFor = HttpApiException.class,
            recover = LARGE_SCALE_ALARM_EVENT_RETRY,
            maxAttempts = 5,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLargeScaleAlarmEvent(LargeScaleAlarmEvent event) {

        try {
            User receiver = userRepository.findById(event.userId())
                    .orElseThrow();

            userAlarmRepository.save(UserAlarm.builder()
                    .title(event.title())
                    .content(event.content())
                    .receiver(receiver)
                    .alarmType(event.alarmType())
                    .build());

            smsRedisTemplate.opsForZSet()
                    .remove(RESULT_DURATION_ALARM_ZSET, event.payload());

            decideResultDurationService.executeAlarmScheduler(event.resultDuration());

        } catch (HttpServerErrorException |
                ResourceAccessException e) {

            log.error("유저 알람 이벤트 실패 userId={} alarmType={}", event.userId(), event.alarmType(), e);

            throw new HttpApiException(e);
        }
    }

    @Recover
    public void recoverLargeScaleAlarmEvent(HttpApiException e, LargeScaleAlarmEvent event) {

        smsRedisTemplate.opsForZSet()
                .remove(RESULT_DURATION_ALARM_ZSET, event.payload());

        smsRedisTemplate.opsForZSet()
                .add(FAILED_ZSET, event.payload(), Instant.now().getEpochSecond());

        log.error("유저 알람 이벤트 최종 실패 userId={} alarmType={}", event.userId(), event.alarmType(), e);

        throw new SmsEventFinalFailedException(e);
    }
}
