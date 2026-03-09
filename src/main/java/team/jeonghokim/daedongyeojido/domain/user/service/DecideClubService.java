package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.UserAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.UserAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.alarm.exception.AlarmNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotAcceptedException;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideClubRequest;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

@Service
@RequiredArgsConstructor
public class DecideClubService {
    private final UserFacade userFacade;
    private final SubmissionRepository submissionRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;
    private final UserAlarmRepository userAlarmRepository;

    @Transactional
    public void execute(DecideClubRequest request) {
        User applicant = userFacade.getCurrentUser();

        UserAlarm alarm = userAlarmRepository.findById(request.getAlarmId())
                .orElseThrow(() -> AlarmNotFoundException.EXCEPTION);

        alarm.executed();

        Submission submission = submissionRepository.findByUserIdAndClubId(applicant.getId(), alarm.getClub().getId())
                .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION);

        validate(applicant, submission, alarm);

        if (request.getIsSelected()) {
            applicant.selectedClub(submission.getApplicationForm().getClub());
            joinClub(submission.getApplicationForm().getClub(), applicant);
        } else {
            refuseClub(submission.getApplicationForm().getClub(), applicant);
        }
    }

    private void validate(User applicant, Submission submission, UserAlarm alarm) {
        if (!applicant.getId().equals(submission.getUser().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (applicant.getClub() != null) {
            throw AlreadyJoinClubException.EXCEPTION;
        }

        if (submission.getUserApplicationStatus() != ApplicationStatus.ACCEPTED) {
            throw ApplicationNotAcceptedException.EXCEPTION;
        }

        if (!alarm.getReceiver().getId().equals(applicant.getId())) {
            throw AlarmAccessDeniedException.EXCEPTION;
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
