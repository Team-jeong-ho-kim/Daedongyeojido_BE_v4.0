package team.jeonghokim.daedongyeojido.global.solapi.dto.response;

public record SmsResponse(
        String groupId,
        String messageId,
        String status
) {
}
