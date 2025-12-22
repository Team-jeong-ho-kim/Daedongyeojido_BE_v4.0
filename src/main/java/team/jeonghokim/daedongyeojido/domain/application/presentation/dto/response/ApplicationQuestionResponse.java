package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;

@Builder
public record ApplicationQuestionResponse(
        Long applicationQuestionId,
        String content
) {

    public static ApplicationQuestionResponse from(ApplicationQuestion question) {
        return ApplicationQuestionResponse.builder()
                .applicationQuestionId(question.getId())
                .content(question.getContent())
                .build();
    }
}
