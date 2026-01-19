package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;

@Builder
public record SchedulerAlarmPayload(

        Long userId,
        Long clubId,
        @JsonProperty("alarm_type") AlarmType alarmType,
        @JsonProperty("is_passed") boolean isPassed
) {
}
