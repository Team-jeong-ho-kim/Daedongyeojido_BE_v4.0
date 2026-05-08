package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

public record SubmitCommentResponse(
    String commentWriter,
    String comment
) {
    public static SubmitCommentResponse of(String commentWriter, String comment) {
        return new SubmitCommentResponse(commentWriter, comment);
    }
}
