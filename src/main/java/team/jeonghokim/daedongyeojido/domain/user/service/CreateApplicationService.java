package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.application.exception.InvalidApplicationQuestionException;
import team.jeonghokim.daedongyeojido.domain.application.facade.ApplicationFormFacade;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.AlreadyApplicationExistException;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.submission.domain.repository.SubmissionRepository;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateApplicationService {
    private static final String SUBMISSION_UNIQUE_CONSTRAINT = "unique_idx_submission_account_form";

    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;
    private final ApplicationFormFacade applicationFormFacade;

    @Transactional
    public void execute(Long applicationFormId, SubmissionRequest request) {

        User user = userFacade.getCurrentUser();

        ApplicationForm applicationForm = applicationFormFacade.getApplicationById(applicationFormId);

        if (submissionRepository.existsByUserIdAndApplicationFormId(user.getId(), applicationForm.getId())) {
            throw AlreadyApplicationExistException.EXCEPTION;
        }

        try {
            submissionRepository.saveAndFlush(Submission.builder()
                            .userName(request.getUserName())
                            .classNumber(request.getClassNumber())
                            .introduction(request.getIntroduction())
                            .answers(createAnswer(request, applicationForm))
                            .major(request.getMajor())
                            .user(user)
                            .applicationForm(applicationForm)
                            .userApplicationStatus(ApplicationStatus.WRITING)
                    .build());
        } catch (DataIntegrityViolationException e) {
            if (isSubmissionUniqueConstraintViolation(e)) {
                throw AlreadyApplicationExistException.EXCEPTION;
            }
            throw e;
        }
    }

    private boolean isSubmissionUniqueConstraintViolation(DataIntegrityViolationException e) {
        Throwable cause = e;

        while (cause != null) {
            if (cause instanceof ConstraintViolationException constraintViolationException) {
                String constraintName = constraintViolationException.getConstraintName();
                if (constraintName != null && constraintName.contains(SUBMISSION_UNIQUE_CONSTRAINT)) {
                    return true;
                }
            }

            String message = cause.getMessage();
            if (message != null && message.contains(SUBMISSION_UNIQUE_CONSTRAINT)) {
                return true;
            }

            cause = cause.getCause();
        }

        return false;
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
