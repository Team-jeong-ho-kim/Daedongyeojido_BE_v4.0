package team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.response;

public record CoolSmsResponse(
        String groupId,
        String messageId,
        String status
) {
}
