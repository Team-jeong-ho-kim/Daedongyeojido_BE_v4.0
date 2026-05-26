package team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
    @NotBlank(message = "댓글이 공백일 수 없습니다.")
    String feedback
) {
}
