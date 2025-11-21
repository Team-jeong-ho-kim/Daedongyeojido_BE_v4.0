package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotSubmittedException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QuerySubmissionDetailService {

    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QuerySubmissionDetailResponse execute(Long submissionId) {
        User user = userFacade.getCurrentUser();
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        validate(user, submission);

        return QuerySubmissionDetailResponse.from(submission);
    }

    public void validate(User user, Submission submission) {
        if (!user.getClub().getId().equals(submission.getApplicationForm().getClub().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (!(submission.isSubmitted())) {
            throw ApplicationNotSubmittedException.EXCEPTION;
        }
    }
}
