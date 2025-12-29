package team.jeonghokim.daedongyeojido.infrastructure.event.listner.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.UserSmsEvent;
import team.jeonghokim.daedongyeojido.infrastructure.sms.service.SmsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsEventListener {
    private final SmsService smsService;

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
}
