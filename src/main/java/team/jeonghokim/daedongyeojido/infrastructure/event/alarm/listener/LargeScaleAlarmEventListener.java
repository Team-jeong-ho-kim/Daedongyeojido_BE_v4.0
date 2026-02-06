package team.jeonghokim.daedongyeojido.infrastructure.event.alarm.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
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
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.LargeScaleAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.SmsEventFinalFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;

import java.time.Instant;

import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.FAILED_ZSET;
import static team.jeonghokim.daedongyeojido.infrastructure.redis.key.RedisKey.RESULT_DURATION_ALARM_ZSET;

@Slf4j
@Component
@RequiredArgsConstructor
public class LargeScaleAlarmEventListener {

    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, SchedulerAlarmPayload> alarmRedisTemplate;
    private final DecideResultDurationService decideResultDurationService;

    private static final String LARGE_SCALE_ALARM_EVENT_RETRY = "recoverLargeScaleAlarmEvent";
    private final SubmissionRepository submissionRepository;

    @Async("largeScaleAlarmExecutor")
    @Retryable(
            retryFor = {DataAccessException.class, RedisConnectionFailureException.class },
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

            Submission submission = submissionRepository.findByUserIdAndClubId(receiver.getId(), event.clubId())
                    .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION);

            submission.applyPassResult(event.isPassed());

            userAlarmRepository.save(UserAlarm.builder()
                    .title(event.title())
                    .content(event.content())
                    .receiver(receiver)
                    .alarmType(event.alarmType())
                    .build());

            alarmRedisTemplate.opsForZSet()
                    .remove(RESULT_DURATION_ALARM_ZSET, event.payload());

            decideResultDurationService.executeAlarmScheduler(event.resultDuration());

        } catch (Exception e) {

            log.error("유저 알람 이벤트 실패 userId={} alarmType={}", event.userId(), event.alarmType(), e);

            throw e;
        }
    }

    @Recover
    public void recoverLargeScaleAlarmEvent(RuntimeException e, LargeScaleAlarmEvent event) {

        alarmRedisTemplate.opsForZSet()
                .remove(RESULT_DURATION_ALARM_ZSET, event.payload());

        alarmRedisTemplate.opsForZSet()
                .add(FAILED_ZSET, event.payload(), Instant.now().getEpochSecond());

        log.error("유저 알람 이벤트 최종 실패 userId={} alarmType={}", event.userId(), event.alarmType(), e);

        throw new SmsEventFinalFailedException(e);
    }
}
