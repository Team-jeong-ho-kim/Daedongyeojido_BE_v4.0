package team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request;

public record CommentRequest(
    Long onePagerId,
    String comment,
    String commentWriter
) {
}
