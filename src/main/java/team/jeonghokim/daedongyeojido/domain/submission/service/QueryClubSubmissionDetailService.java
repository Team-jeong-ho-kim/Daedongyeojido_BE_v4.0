package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotSubmittedException;
import team.jeonghokim.daedongyeojido.domain.club.exception.UserNotInClubException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryClubSubmissionDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryClubSubmissionDetailService {

    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryClubSubmissionDetailResponse execute(Long submissionId) {
        User user = userFacade.getCurrentUser();
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        validate(user, submission);

        return QueryClubSubmissionDetailResponse.from(submission);
    }

    private void validate(User user, Submission submission) {
        if (user.getClub() == null) {
            throw UserNotInClubException.EXCEPTION;
        }

        if (!user.getClub().getId().equals(submission.getApplicationForm().getClub().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (!(submission.isSubmitted())) {
            throw ApplicationNotSubmittedException.EXCEPTION;
        }
    }
}
