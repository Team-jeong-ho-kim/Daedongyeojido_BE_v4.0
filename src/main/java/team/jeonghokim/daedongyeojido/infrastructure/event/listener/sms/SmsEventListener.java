package team.jeonghokim.daedongyeojido.infrastructure.event.listener.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.LargeScaleSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;

import static team.jeonghokim.daedongyeojido.infrastructure.scheduler.service.SchedulerService.RESULT_DURATION_ZSET;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsEventListener {
    private final SmsService smsService;
    private final RedisTemplate<String, SchedulerPayload> smsRedisTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserSmsEvent(UserSmsEvent event) {
        try {
            smsService.send(
                    event.phoneNumber(),
                    event.message(),
                    event.clubName()
            );

        } catch (Exception e) {
            log.error("유저 SMS 이벤트 실패: phoneNumber={} message={}",
                    event.phoneNumber(), event.message(), e);
        }
    }

    @Async("largeScaleExecutor")
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
        }
    }
}
