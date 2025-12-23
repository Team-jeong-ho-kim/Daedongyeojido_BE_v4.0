package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotSubmittedException;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.facade.SubmissionFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class CancelApplicationService {
    private final SubmissionFacade submissionFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long submissionId) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionFacade.getApplicationBySubmissionId(submissionId);

        if (!user.getId().equals(submission.getUser().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (!(submission.isSubmitted())) {
            throw ApplicationNotSubmittedException.EXCEPTION;
        }

        submission.cancel();
        cancelApplication(submission.getApplicationForm().getClub(), user);
    }

    private void cancelApplication(Club club, User user) {
        ClubAlarm alarm = ClubAlarm.builder()
                .title(AlarmType.USER_CANCEL_APPLICATION.formatTitle(user.getUserName()))
                .content(AlarmType.USER_CANCEL_APPLICATION.formatContent(user.getUserName()))
                .club(club)
                .alarmType(AlarmType.USER_CANCEL_APPLICATION)
                .build();

        club.getAlarms().add(alarm);
    }
}
