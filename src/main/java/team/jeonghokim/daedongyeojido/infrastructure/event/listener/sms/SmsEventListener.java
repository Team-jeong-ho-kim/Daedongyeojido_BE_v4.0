package team.jeonghokim.daedongyeojido.infrastructure.event.listener.sms;

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
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.LargeScaleSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.HttpApiException;
import team.jeonghokim.daedongyeojido.infrastructure.event.exception.SmsEventFinalFailedException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;

import static team.jeonghokim.daedongyeojido.infrastructure.scheduler.service.SchedulerService.RESULT_DURATION_ZSET;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsEventListener {
    private final SmsService smsService;
    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;

    private static final String SMS_EVENT_RETRY = "recoverSmsEvent";
    private static final String LARGE_SCALE_EVENT_RETRY = "recoverLargeScaleSmsEvent";

    @Async
    @Retryable(
            retryFor = HttpApiException.class,
            recover = SMS_EVENT_RETRY,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserSmsEvent(UserSmsEvent event) {

        try {
            smsService.send(
                    event.phoneNumber(),
                    event.message(),
                    event.clubName()
            );

        } catch (HttpServerErrorException |
                 ResourceAccessException e) {

            log.warn("유저 SMS 이벤트 실패: phoneNumber={} message={}",
                    event.phoneNumber(), event.message(), e);

            throw new HttpApiException(e);
        }
    }

    @Async("largeScaleExecutor")
    @Retryable(
            retryFor = HttpApiException.class,
            recover = LARGE_SCALE_EVENT_RETRY,
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
                    .remove(RESULT_DURATION_ZSET, event.payload());

        } catch (Exception e) {

            log.error("유저 SMS 이벤트 실패: phoneNumber={} message={}",
                    event.phoneNumber(), event.message(), e);

            throw new HttpApiException(e);
        }
    }

    @Recover
    public void recoverSmsEvent(HttpApiException e, UserSmsEvent event) {

        log.error("SMS 이벤트 최종 실패: phoneNumber={} message={}",
                event.phoneNumber(), event.message(), e);

        throw new SmsEventFinalFailedException(e);
    }

    @Recover
    public void recoverLargeScaleSmsEvent(HttpApiException e, LargeScaleSmsEvent event) {

        log.error("SMS 이벤트 최종 실패: phoneNumber={} message={}",
                event.phoneNumber(), event.message(), e);

        throw new SmsEventFinalFailedException(e);
    }
}
