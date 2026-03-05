package team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String classNumber,
        String userName,
        Role role
) {
}
