package team.jeonghokim.daedongyeojido.infrastructure.event.domain.user;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;

@Builder
public record UserAlarmEvent(
        String title,
        String content,
        Long userId,
        AlarmType alarmType
) {
}
