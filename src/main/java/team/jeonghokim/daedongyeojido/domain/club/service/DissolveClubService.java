package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DissolveClubService {

    private final ClubFacade clubFacade;
    private final UserFacade userFacade;
    private final AlarmRepository alarmRepository;

    @Transactional
    public void execute(Long clubId) {
        Club club = clubFacade.getClubById(clubId);
        User receiver = userFacade.getCurrentUser();

        // sms 알림 기능 로직 추가 해야 함.

        Alarm alarm = Alarm.builder()
                .title(AlarmType.DISSOLVE_CLUB.getTitle())
                .content(AlarmType.DISSOLVE_CLUB.format(club.getClubName()))
                .club(club)
                .receiver(receiver)
                .build();

        alarmRepository.save(alarm);
    }
}
