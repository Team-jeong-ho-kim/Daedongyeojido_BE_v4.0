package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

public record CommentRequest(
    Long onePagerId,
    String comment,
    String commentWriter
) {
}
