package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

public record SubmitCommentResponse(
    String TeacherName,
    String comment
) {
    public static SubmitCommentResponse of(String TeacherName, String comment) {
        return new SubmitCommentResponse(TeacherName, comment);
    }
}
