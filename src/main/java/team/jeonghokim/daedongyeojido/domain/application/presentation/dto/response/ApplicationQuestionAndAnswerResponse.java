package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

public record ApplicationQuestionAndAnswerResponse(
        Long applicationQuestionId,
        String question,
        Long applicationAnswerId,
        String answer
) {
}
