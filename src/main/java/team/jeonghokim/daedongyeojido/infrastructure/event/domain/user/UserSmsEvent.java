package team.jeonghokim.daedongyeojido.infrastructure.event.domain.user;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Builder
public record UserSmsEvent(
        String phoneNumber,
        Message message,
        String clubName
) {
}
