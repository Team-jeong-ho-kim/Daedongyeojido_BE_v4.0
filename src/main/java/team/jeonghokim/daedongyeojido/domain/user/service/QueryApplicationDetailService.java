package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.facade.SubmissionFacade;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryApplicationDetailService {
    private final UserFacade userFacade;
    private final SubmissionFacade submissionFacade;

    @Transactional(readOnly = true)
    public QueryApplicationDetailResponse execute(Long submissionId) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionFacade.getApplicationBySubmissionId(submissionId);

        if (!submission.getUser().getId().equals(user.getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        return QueryApplicationDetailResponse.of(submission);
    }
}
