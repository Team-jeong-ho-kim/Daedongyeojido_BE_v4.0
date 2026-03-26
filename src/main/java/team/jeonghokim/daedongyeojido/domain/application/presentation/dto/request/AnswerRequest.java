package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AnswerRequest {

    @NotEmpty(message = "존재하지 않는 질문입니다.")
    private Long applicationQuestionId;

    @Size(max = 500, message = "답은 최대 500자까지 작성할 수 있습니다.")
    private String answer;
}
