package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationQuestion;

public record ApplicationQuestionResponse(
        Long applicationQuestionId,
        String content
) {
    public static ApplicationQuestionResponse of(ApplicationQuestion question) {
        return new ApplicationQuestionResponse(
                question.getId(),
                question.getContent()
        );
    }
}
