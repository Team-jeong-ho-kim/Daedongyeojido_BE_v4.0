package team.jeonghokim.daedongyeojido.infrastructure.feign.solapi.dto.response;

public record SmsResponse(
        String groupId,
        String messageId,
        String status
) {
}
