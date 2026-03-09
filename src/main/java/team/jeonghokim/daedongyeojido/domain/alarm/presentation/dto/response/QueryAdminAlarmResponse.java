package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import java.util.List;

public record QueryAdminAlarmResponse(List<AlarmResponse> alarms) {

    public static QueryAdminAlarmResponse from(List<AlarmResponse> alarms) {
        return new QueryAdminAlarmResponse(alarms);
    }
}
