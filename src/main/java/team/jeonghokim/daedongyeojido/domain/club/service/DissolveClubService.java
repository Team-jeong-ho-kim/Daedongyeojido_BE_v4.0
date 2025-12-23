package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DissolveClubService {

    private final UserFacade userFacade;
    private final UserAlarmRepository userAlarmRepository;

    @Transactional
    public void execute() {
        User receiver = userFacade.getCurrentUser();

        Club club = Optional.ofNullable(receiver.getClub())
                .orElseThrow(() -> UserNotInClubException.EXCEPTION);

        // sms 알림 기능 로직 추가 해야 함.

        UserAlarm alarm = UserAlarm.builder()
                .title(AlarmType.DISSOLVE_CLUB_APPLY.formatTitle(club.getClubName()))
                .content(AlarmType.DISSOLVE_CLUB_APPLY.formatContent(club.getClubName()))
                .receiver(receiver)
                .alarmType(AlarmType.DISSOLVE_CLUB_APPLY)
                .build();

        userAlarmRepository.save(alarm);
    }
}
