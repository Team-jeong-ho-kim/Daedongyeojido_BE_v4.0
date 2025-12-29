package team.jeonghokim.daedongyeojido.infrastructure.event.listner.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.UserAlarmEvent;

import java.net.SocketTimeoutException;
import java.net.http.HttpTimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAlarmEventListener {
    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;

    private static final String USER_EVENT_RETRY = "recoverUserEvent";

    @Async
    @Retryable(
            retryFor = {
                    HttpTimeoutException.class,
                    SocketTimeoutException.class,
                    HttpServerErrorException.class,
            },
            recover = USER_EVENT_RETRY,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserAlarmEvent(UserAlarmEvent event) {
        User receiver = userRepository.findById(event.userId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        UserAlarm alarm = userAlarmRepository.save(UserAlarm.builder()
                .title(event.title())
                .content(event.content())
                .receiver(receiver)
                .alarmType(event.alarmType())
                .build());

        receiver.getAlarms().add(alarm);
    }

    @Recover
    public void recoverUserEvent(Exception e, UserAlarmEvent event) {
        log.error("유저 알람 이벤트 최종 실패: clubId={} alarmType={}",
                event.userId(), event.alarmType(), e);
    }
}
