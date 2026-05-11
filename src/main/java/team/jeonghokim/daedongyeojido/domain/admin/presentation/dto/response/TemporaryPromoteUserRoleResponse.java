package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response;

public record TemporaryPromoteUserRoleResponse(
        Long id,
        String accountId,
        String userName,
        String role
) {
}
