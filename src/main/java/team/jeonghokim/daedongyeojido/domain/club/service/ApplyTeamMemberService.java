package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.TeamMemberRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.AlreadyUserApplicationExistException;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

@Service
@RequiredArgsConstructor
public class ApplyTeamMemberService {
    private final UserApplicationRepository userApplicationRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;

    @Transactional
    public void execute(TeamMemberRequest request) {

        User user = userFacade.getCurrentUser();

        User userApplicant = userRepository.findByUserNameAndClassNumber(request.getUserName(), request.getClassNumber())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        UserApplication userApplication = userApplicationRepository.save(UserApplication.builder()
                .user(userApplicant)
                .isApproved(false)
                .club(user.getClub())
                .build());

        if (userApplication.getUser().getId().equals(user.getId())) {
            throw AlreadyUserApplicationExistException.EXCEPTION;
        }

        createAlarm(user.getClub(), userApplicant);
    }

    private void createAlarm(Club club, User userApplication) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(userApplication, club, AlarmType.CLUB_MEMBER_APPLY)
        );
    }
}
