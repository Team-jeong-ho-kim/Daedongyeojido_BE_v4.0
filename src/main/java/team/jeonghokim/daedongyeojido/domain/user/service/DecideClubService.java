package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotAcceptedException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.facade.SubmissionFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.exception.AlreadySelectClubException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideClubRequest;

@Service
@RequiredArgsConstructor
public class DecideClubService {
    private final UserFacade userFacade;
    private final SubmissionFacade submissionFacade;

    @Transactional
    public void execute(Long submissionId, DecideClubRequest request) {
        User applicant = userFacade.getCurrentUser();

        Submission submission = submissionFacade.getApplicationBySubmissionId(submissionId);

        validate(applicant, submission);

        if (request.getIsSelected()) {
            applicant.selectedClub(submission.getApplicationForm().getClub());
        }
    }

    private void validate(User applicant, Submission submission) {
        if (!applicant.getId().equals(submission.getUser().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (applicant.getClub() != null) {
            throw AlreadySelectClubException.EXCEPTION;
        }

        if (submission.getApplicationStatus() != ApplicationStatus.ACCEPTED) {
            throw ApplicationNotAcceptedException.EXCEPTION;
        }
    }
}
