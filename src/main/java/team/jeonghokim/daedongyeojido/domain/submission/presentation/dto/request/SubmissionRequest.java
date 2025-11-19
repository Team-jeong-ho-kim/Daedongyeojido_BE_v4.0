package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.AnswerRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

@Getter
public class SubmissionRequest {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 4, message = "이름은 최대 4자까지 작성할 수 있습니다.")
    @JsonProperty("userName")
    private String userName;

    @NotBlank(message = "학번을 입력해주세요.")
    @Size(max = 4, message = "학번은 최대 4자까지 작성할 수 있습니다.")
    @JsonProperty("classNumber")
    private String classNumber;

    @Size(max = 300, message = "자기소개는 최대 300자까지 작성할 수 있습니다.")
    private String introduction;

    @NotEmpty(message = "최소 하나 이상의 전공을 선택해주세요.")
    private Major major;

    private List<AnswerRequest> answer;
}
