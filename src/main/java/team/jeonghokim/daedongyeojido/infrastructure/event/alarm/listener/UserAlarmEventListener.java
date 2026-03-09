package team.jeonghokim.daedongyeojido.infrastructure.event.alarm.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event.UserAlarmEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAlarmEventListener {

    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserAlarmEvent(UserAlarmEvent event) {

        try {
            User receiver = userRepository.findById(event.userId())
                    .orElseThrow();

            Club club = event.clubId() != null
                    ? clubRepository.findById(event.clubId()).orElse(null)
                    : null;

            userAlarmRepository.save(UserAlarm.builder()
                    .title(event.title())
                    .content(event.content())
                    .receiver(receiver)
                    .alarmType(event.alarmType())
                    .club(club)
                    .build());
        } catch (Exception e) {

            log.error("유저 알람 이벤트 실패 userId={} alarmType={}", event.userId(), event.alarmType(), e);

            throw e;
        }
    }
}
