package team.jeonghokim.daedongyeojido.domain.submission.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;

@Component
@RequiredArgsConstructor
public class SubmissionFacade {
    private final SubmissionRepository submissionRepository;

    public Submission getApplicationBySubmissionId(Long submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }
}
