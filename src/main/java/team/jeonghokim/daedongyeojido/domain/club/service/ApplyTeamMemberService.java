package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.TeamMemberRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class ApplyTeamMemberService {
    private final UserApplicationRepository userApplicationRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(TeamMemberRequest request) {
        User user = userFacade.getCurrentUser();

        User userApplication = userRepository.findByUserNameAndClassNumber(request.getUserName(), request.getClassNumber())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userApplicationRepository.save(UserApplication.builder()
                .user(userApplication)
                .isApproved(false)
                .club(user.getClub())
                .build());

        createAlarm(user.getClub(), userApplication);
    }

    private void createAlarm(Club club, User userApplication) {
        Alarm alarm = Alarm.builder()
                .title(AlarmType.CLUB_MEMBER_APPLY.getTitle())
                .content(AlarmType.CLUB_MEMBER_APPLY.format(club.getClubName()))
                .club(club)
                .receiver(userApplication)
                .alarmType(AlarmType.CLUB_MEMBER_APPLY)
                .build();

        userApplication.getAlarms().add(alarm);
    }
}
