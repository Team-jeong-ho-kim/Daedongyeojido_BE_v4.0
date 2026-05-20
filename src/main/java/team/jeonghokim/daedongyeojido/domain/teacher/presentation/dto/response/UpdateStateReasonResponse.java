package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response;

public record UpdateStateReasonResponse(
    String reason
) {
    public static UpdateStateReasonResponse of(String reason) {
        return new UpdateStateReasonResponse(reason);
    }
}
