package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideTeamMemberApplicationRequest;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

@Service
@RequiredArgsConstructor
public class DecideTeamMemberApplicationService {
    private final UserFacade userFacade;
    private final UserApplicationRepository userApplicationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;

    @Transactional
    public void execute(DecideTeamMemberApplicationRequest request) {

        User user = userFacade.getCurrentUser();

        UserApplication userApplication = userApplicationRepository.findByUser_IdAndClub_Id(user.getId(), request.getClubId())
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

        eventPublisher.publishEvent(
                alarmEventFactory.createClubAlarmEvent(club, user, AlarmType.USER_JOINED_CLUB)
        );
    }

    private void refuseClub(Club club, User user) {

        eventPublisher.publishEvent(
                alarmEventFactory.createClubAlarmEvent(club, user, AlarmType.USER_REFUSED_CLUB)
        );
    }
}
