package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

@Builder
public record SchedulerAlarmPayload(

        User user,
        Club club,
        AlarmType alarmType
) {
}
