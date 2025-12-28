package team.jeonghokim.daedongyeojido.infrastructure.event.domain.club;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;

@Builder
public record ClubAlarmEvent(
        String title,
        String content,
        Long clubId,
        AlarmType alarmType
) {
}
