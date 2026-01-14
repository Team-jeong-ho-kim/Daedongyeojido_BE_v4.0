package team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "계정 ID를 입력해주세요.")
        @JsonProperty("account_id")
        String accountId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
