package team.jeonghokim.daedongyeojido.infrastructure.event.sms.event;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Builder
public record UserSmsEvent(
        Long smsHistoryId,
        String phoneNumber,
        Message message,
        String clubName
) {
}
