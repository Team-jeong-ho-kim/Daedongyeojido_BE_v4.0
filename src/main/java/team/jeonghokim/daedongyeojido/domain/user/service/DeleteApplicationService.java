package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.exception.CannotDeleteApplicationException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class DeleteApplicationService {
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long submissionId) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        if (!user.getId().equals(submission.getUser().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (submission.getApplicationStatus() != ApplicationStatus.WRITING) {
            throw CannotDeleteApplicationException.EXCEPTION;
        }

        submissionRepository.delete(submission);
    }
}
