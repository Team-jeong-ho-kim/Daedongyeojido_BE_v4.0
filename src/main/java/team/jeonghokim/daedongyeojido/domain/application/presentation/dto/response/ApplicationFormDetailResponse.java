package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;

import java.time.LocalDate;
import java.util.List;

public record ApplicationFormDetailResponse(
        List<String> content,
        LocalDate submissionDuration
) {
    public static ApplicationFormDetailResponse of(ApplicationForm applicationForm) {
        return new ApplicationFormDetailResponse(
                applicationForm.getApplicationQuestions().stream().map(ApplicationQuestion::getContent).toList(),
                applicationForm.getSubmissionDuration()
        );
    }
}
