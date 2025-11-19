package team.jeonghokim.daedongyeojido.domain.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationAnswer;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;
import team.jeonghokim.daedongyeojido.domain.application.domain.enums.ApplicationStatus;
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
public class CreateSubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserFacade userFacade;
    private final ApplicationFormFacade applicationFormFacade;

    @Transactional
    public void execute(Long applicationFormId, SubmissionRequest request) {
        User user = userFacade.getCurrentUser();

        ApplicationForm applicationForm = applicationFormFacade.getApplicationById(applicationFormId);

        submissionRepository.save(Submission.builder()
                        .userName(request.getUserName())
                        .classNumber(request.getClassNumber())
                        .introduction(request.getIntroduction())
                        .answers(createAnswer(request, applicationForm))
                        .major(request.getMajor())
                        .submissionDuration(applicationForm.getSubmissionDuration())
                        .user(user)
                        .applicationForm(applicationForm)
                        .applicationStatus(ApplicationStatus.WRITING)
                .build());
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
