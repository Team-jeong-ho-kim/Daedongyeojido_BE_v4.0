package team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String classNumber,
        String userName
) {
}
