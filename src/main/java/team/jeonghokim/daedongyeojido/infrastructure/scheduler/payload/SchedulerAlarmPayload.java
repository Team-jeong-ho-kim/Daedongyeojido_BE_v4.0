package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

@Builder
public record SchedulerAlarmPayload(

        Long userId,
        Club club,
        @JsonProperty("is_passed") AlarmType alarmType
) {
}
