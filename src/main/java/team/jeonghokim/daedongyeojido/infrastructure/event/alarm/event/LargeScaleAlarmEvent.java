package team.jeonghokim.daedongyeojido.infrastructure.event.alarm.event;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerAlarmPayload;

@Builder
public record LargeScaleAlarmEvent(

        AlarmType alarmType,
        Long userId,
        String title,
        String content,
        SchedulerAlarmPayload payload,
        ResultDuration resultDuration
) {
}
