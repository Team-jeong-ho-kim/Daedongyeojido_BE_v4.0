package team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request;

public record LoginRequest(
        String accountId,
        String password
) {
}
