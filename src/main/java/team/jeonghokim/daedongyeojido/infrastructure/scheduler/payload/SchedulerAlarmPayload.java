package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;

@Builder
public record SchedulerAlarmPayload(

        Long userId,
        Long clubId,
        AlarmType alarmType,
        boolean isPassed
) {
}
