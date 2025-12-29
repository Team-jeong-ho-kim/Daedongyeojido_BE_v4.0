package team.jeonghokim.daedongyeojido.infrastructure.event.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.ClubAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.club.ClubAlarmEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmEventListener {
    private final UserAlarmRepository userAlarmRepository;
    private final ClubAlarmRepository clubAlarmRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleClubAlarmEvent(ClubAlarmEvent event) {
        try {
            Club club = clubRepository.findById(event.clubId())
                    .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

            clubAlarmRepository.save(ClubAlarm.builder()
                            .title(event.title())
                            .content(event.content())
                            .club(club)
                            .alarmType(event.alarmType())
                    .build());
        } catch (Exception e) {
            log.info("동아리 알람 이벤트 실패: clubId={} alarmType={}",
                    event.clubId(), event.alarmType(), e);
        }
    }
}
