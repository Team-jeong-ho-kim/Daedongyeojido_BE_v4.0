package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryApplicationDetailService {
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryApplicationDetailResponse execute(Long submissionId) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        if (!submission.getUser().getId().equals(user.getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        return QueryApplicationDetailResponse.of(submission);
    }
}
