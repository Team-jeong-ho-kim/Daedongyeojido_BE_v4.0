package team.jeonghokim.daedongyeojido.domain.submission.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;

@Configuration
@RequiredArgsConstructor
public class SubmissionFacade {
    private final SubmissionRepository submissionRepository;

    public Submission getApplicationBySubmissionId(Long submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
    }
}
