package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.onepager.domain.RejectedOnePagerComment;

import java.time.LocalDate;

public record RejectedOnePagerCommentResponse(
        Long id,
        String content,
        String writer
) {
    public static RejectedOnePagerCommentResponse from(RejectedOnePagerComment rejectedOnePagerComment) {
        return new RejectedOnePagerCommentResponse(
                rejectedOnePagerComment.getId(),
                rejectedOnePagerComment.getComment(),
                rejectedOnePagerComment.getCommentWriter()
        );
    }
}
