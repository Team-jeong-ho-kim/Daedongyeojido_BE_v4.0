package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionDetailResponse;

@Service
@RequiredArgsConstructor
public class QuerySubmissionDetailService {

    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QuerySubmissionDetailResponse execute(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION);

        return QuerySubmissionDetailResponse.from(submission);
    }
}
