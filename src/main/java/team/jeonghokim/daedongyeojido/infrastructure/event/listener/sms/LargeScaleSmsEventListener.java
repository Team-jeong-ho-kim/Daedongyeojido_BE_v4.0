package team.jeonghokim.daedongyeojido.infrastructure.event.listener.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.LargeScaleSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.HttpApiException;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.SmsEventFinalFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.service.SchedulerService;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;

import java.time.Instant;
import java.time.ZoneId;

@Slf4j
@Component
@RequiredArgsConstructor
public class LargeScaleSmsEventListener {

    private final SmsService smsService;
    private final RedisTemplate<String, SchedulerSmsPayload> smsRedisTemplate;
    private final SchedulerService schedulerService;
    private final ResultDurationRepository resultDurationRepository;
    private final TaskScheduler taskScheduler;
    private final DecideResultDurationService decideResultDurationService;

    private static final String LARGE_SCALE_SMS_EVENT_RETRY = "recoverLargeScaleSmsEvent";
    private static final String FAILED_ZSET  = "club:result-duration:failed";
    private static final String RESULT_DURATION_SMS_ZSET = "club:result-duration-sms";
    private static final String TIME_ZONE = "Asia/Seoul";

    @Async("largeScaleSmsExecutor")
    @Retryable(
            retryFor = HttpApiException.class,
            recover = LARGE_SCALE_SMS_EVENT_RETRY,
            maxAttempts = 5,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLargeScaleSmsEvent(LargeScaleSmsEvent event) {

        try {
            smsService.send(
                    event.phoneNumber(),
                    event.message(),
                    event.clubName()
            );

            smsRedisTemplate.opsForZSet()
                    .remove(RESULT_DURATION_SMS_ZSET, event.payload());

            decideResultDurationService.executeSmsScheduler(event.resultDuration());

        } catch (HttpServerErrorException |
                 ResourceAccessException e) {

            log.error("유저 SMS 이벤트 실패: phoneNumber={} message={}",
                    event.phoneNumber(), event.message(), e);

            throw new HttpApiException(e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initSmsSchedule() {

        resultDurationRepository.findSmsStatusPendingResultDuration()
                .ifPresent(resultDuration -> {

                    Instant executeTime = resultDuration.getResultDurationTime()
                            .atZone(ZoneId.of(TIME_ZONE))
                            .toInstant();

                    if (executeTime.isBefore(Instant.now())) {
                        schedulerService.execute();
                        return;
                    }

                    taskScheduler.schedule(schedulerService::execute, executeTime);
                });
    }

    @Recover
    public void recoverLargeScaleSmsEvent(HttpApiException e, LargeScaleSmsEvent event) {

        smsRedisTemplate.opsForZSet()
                .remove(RESULT_DURATION_SMS_ZSET, event.payload());

        smsRedisTemplate.opsForZSet()
                .add(FAILED_ZSET, event.payload(), Instant.now().getEpochSecond());

        log.error("SMS 이벤트 최종 실패: phoneNumber={} message={}",
                event.phoneNumber(), event.message(), e);

        throw new SmsEventFinalFailedException(e);
    }
}
