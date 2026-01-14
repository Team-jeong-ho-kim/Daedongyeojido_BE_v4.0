package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AdminAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.factory.AlarmEventFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DissolveClubService {

    private final UserFacade userFacade;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;
    private final AdminAlarmRepository adminAlarmRepository;

    @Transactional
    public void execute() {

        User receiver = userFacade.getCurrentUser();

        Club club = Optional.ofNullable(receiver.getClub())
                .orElseThrow(() -> UserNotInClubException.EXCEPTION);

        createUserAlarm(receiver, club);

        createAdminAlarm(club);
    }

    public void createUserAlarm(User receiver, Club club) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(receiver, club, AlarmType.DISSOLVE_CLUB_APPLY)
        );
    }

    public void createAdminAlarm(Club club) {

        AdminAlarm adminAlarm = AdminAlarm.builder()
                .title(AlarmType.REQUEST_CLUB_DISSOLUTION.formatTitle(club.getClubName()))
                .content(AlarmType.REQUEST_CLUB_DISSOLUTION.formatContent(club.getClubName()))
                .alarmType(AlarmType.REQUEST_CLUB_DISSOLUTION)
                .build();

        adminAlarmRepository.save(adminAlarm);
    }
}
