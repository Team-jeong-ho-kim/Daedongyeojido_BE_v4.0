package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.application.exception.ApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.application.exception.CannotModifyApplicationException;
import team.jeonghokim.daedongyeojido.domain.application.exception.InvalidApplicationQuestionException;
import team.jeonghokim.daedongyeojido.domain.application.facade.ApplicationFormFacade;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateApplicationService {
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;
    private final ApplicationFormFacade applicationFormFacade;

    @Transactional
    public void execute(Long submissionId, SubmissionRequest request) {
        User user = userFacade.getCurrentUser();

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        if (!user.getId().equals(submission.getUser().getId())) {
            throw ApplicationAccessDeniedException.EXCEPTION;
        }

        if (submission.getApplicationStatus() != ApplicationStatus.WRITING) {
            throw CannotModifyApplicationException.EXCEPTION;
        }

        ApplicationForm applicationForm = applicationFormFacade.getApplicationById(submission.getApplicationForm().getId());

        List<ApplicationAnswer> applicationAnswers = createAnswer(request, applicationForm);

        submission.update(
                request.getMajor(),
                request.getIntroduction(),
                request.getUserName(),
                request.getClassNumber(),
                applicationAnswers
        );
    }

    private List<ApplicationAnswer> createAnswer(SubmissionRequest request, ApplicationForm applicationForm) {
        return request.getAnswer().stream()
                .map(answerRequest -> {
                    ApplicationQuestion applicationQuestion = validate(applicationForm, answerRequest.getApplicationQuestionId());
                    return ApplicationAnswer.builder()
                            .content(answerRequest.getAnswer())
                            .applicationQuestion(applicationQuestion)
                            .build();
                })
                .toList();
    }

    private ApplicationQuestion validate(ApplicationForm applicationForm, Long applicationQuestionId) {
        return applicationForm.getApplicationQuestions().stream()
                .filter(applicationQuestion -> applicationQuestion.getId().equals(applicationQuestionId))
                .findFirst()
                .orElseThrow(() -> InvalidApplicationQuestionException.EXCEPTION);
    }
}
