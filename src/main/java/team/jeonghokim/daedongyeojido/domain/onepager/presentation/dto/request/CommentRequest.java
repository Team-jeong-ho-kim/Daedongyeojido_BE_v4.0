package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
    @NotBlank(message = "댓글이 공백일 수 없습니다.")
    String comment,

    @NotBlank(message = "댓글 작성자가 빈칸일 수 없습니다.")
    @Size(min = 2, max = 4)
    String commentWriter
) {
}
