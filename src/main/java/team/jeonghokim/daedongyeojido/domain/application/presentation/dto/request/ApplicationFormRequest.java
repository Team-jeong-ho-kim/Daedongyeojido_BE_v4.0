package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ApplicationFormRequest {

    @NotBlank(message = "질문을 작성해주세요.")
    @Size(max = 150, message = "질문은 150자까지 작성할 수 있습니다.")
    private List<String> content;

    @NotNull(message = "제출 기한을 설정해주세요.")
    private LocalDate submissionDuration;
}
