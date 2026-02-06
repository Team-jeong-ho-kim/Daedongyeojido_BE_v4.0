package team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmCategory;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;

@Builder
public record ClubAlarmEvent(
        String title,
        String content,
        Long clubId,
        AlarmType alarmType,
        AlarmCategory category,
        Long referenceId
) {
}
