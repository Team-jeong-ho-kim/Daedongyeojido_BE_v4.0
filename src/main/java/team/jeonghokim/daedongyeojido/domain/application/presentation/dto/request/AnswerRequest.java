package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AnswerRequest {

    @NotNull(message = "존재하지 않는 질문입니다.")
    private Long applicationFormQuestionId;

    @Size(max = 200, message = "답은 최대 200자까지 작성할 수 있습니다.")
    private String answer;
}
