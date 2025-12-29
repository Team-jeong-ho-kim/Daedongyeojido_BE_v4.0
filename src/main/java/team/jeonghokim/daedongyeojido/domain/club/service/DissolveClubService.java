package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.domain.user.UserAlarmEvent;
import team.jeonghokim.daedongyeojido.infrastructure.event.factory.AlarmEventFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DissolveClubService {

    private final UserFacade userFacade;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;

    @Transactional
    public void execute() {
        User receiver = userFacade.getCurrentUser();

        Club club = Optional.ofNullable(receiver.getClub())
                .orElseThrow(() -> UserNotInClubException.EXCEPTION);

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(receiver, club, AlarmType.DISSOLVE_CLUB_APPLY)
        );
    }
}
