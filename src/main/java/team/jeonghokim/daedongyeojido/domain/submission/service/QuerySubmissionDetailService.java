package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotSubmittedException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionDetailResponse;

@Service
@RequiredArgsConstructor
public class QuerySubmissionDetailService {

    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QuerySubmissionDetailResponse execute(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        if (!(submission.isSubmitted())) {
            throw ApplicationNotSubmittedException.EXCEPTION;
        }

        return QuerySubmissionDetailResponse.from(submission);
    }
}
