package team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Division;

public record LoginRequest(

        @NotNull(message = "구분을 입력해주세요.")
        Division division,

        @NotBlank(message = "계정 ID를 입력해주세요.")
        String accountId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
