package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationFormRequest {

    @NotEmpty(message = "질문을 최소 하나 이상 작성해주세요.")
    private List<@NotBlank(message = "질문을 작성해주세요.")
                 @Size(max = 150, message = "질문은 150자까지 작성할 수 있습니다.")
            String> content;

    @NotBlank(message = "제출 기한을 설정해주세요.")
    private String submissionDuration;
}
