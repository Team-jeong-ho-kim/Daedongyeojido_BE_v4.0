package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ApplicationFormResponse(
        List<String> content,
        LocalDate submissionDuration
) {
    public static ApplicationFormResponse of(ApplicationForm applicationForm) {
        return new ApplicationFormResponse(
                applicationForm.getApplicationQuestions().stream().map(ApplicationQuestion::getContent).toList(),
                applicationForm.getSubmissionDuration()
        );
    }
}
