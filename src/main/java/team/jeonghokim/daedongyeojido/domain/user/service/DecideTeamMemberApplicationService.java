package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideTeamMemberApplicationRequest;

@Service
@RequiredArgsConstructor
public class DecideTeamMemberApplicationService {
    private final UserFacade userFacade;
    private final UserApplicationRepository userApplicationRepository;

    @Transactional
    public void execute(DecideTeamMemberApplicationRequest request) {
        User user = userFacade.getCurrentUser();

        UserApplication userApplication = userApplicationRepository.findByUserId(user.getId())
                .orElseThrow(() -> UserApplicationNotFoundException.EXCEPTION);

        if (request.getIsApproved()) {
            user.approvedTeamMember(userApplication.getClub());
            userApplication.approved();
            joinClub(userApplication.getClub(), user);
        } else {
            refuseClub(userApplication.getClub(), user);
        }
    }

    private void joinClub(Club club, User user) {
        ClubAlarm alarm = ClubAlarm.builder()
                .title(AlarmType.USER_JOINED_CLUB.formatTitle(user.getUserName()))
                .content(AlarmType.USER_JOINED_CLUB.formatContent(user.getUserName()))
                .club(club)
                .alarmType(AlarmType.USER_JOINED_CLUB)
                .build();

        club.getAlarms().add(alarm);
    }

    private void refuseClub(Club club, User user) {
        ClubAlarm alarm = ClubAlarm.builder()
                .title(AlarmType.USER_REFUSED_CLUB.formatTitle(user.getUserName()))
                .content(AlarmType.USER_REFUSED_CLUB.formatContent(user.getUserName()))
                .club(club)
                .alarmType(AlarmType.USER_REFUSED_CLUB)
                .build();

        club.getAlarms().add(alarm);
    }
}
