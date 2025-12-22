package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import java.util.List;

public record QueryUserAlarmResponse(List<AlarmResponse> alarms) {

    public static QueryUserAlarmResponse from(List<AlarmResponse> alarms) {
        return new QueryUserAlarmResponse(alarms);
    }
}
