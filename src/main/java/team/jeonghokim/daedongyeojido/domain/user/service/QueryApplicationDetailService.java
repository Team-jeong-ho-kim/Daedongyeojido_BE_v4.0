package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.exception.SubmissionNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryApplicationDetailService {
    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public QueryApplicationDetailResponse execute(Long submissionId) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> SubmissionNotFoundException.EXCEPTION);

        return QueryApplicationDetailResponse.of(submission);
    }
}
