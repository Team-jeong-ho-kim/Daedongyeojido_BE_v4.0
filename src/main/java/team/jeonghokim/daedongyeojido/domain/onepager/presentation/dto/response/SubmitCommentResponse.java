package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;

public record SubmitCommentResponse(
    String commentWriter,
    String comment
) {
    public static SubmitCommentResponse from(RejectedOnePagerComment comment) {
        return new SubmitCommentResponse(comment.getCommentWriter(), comment.getComment());
    }
}
