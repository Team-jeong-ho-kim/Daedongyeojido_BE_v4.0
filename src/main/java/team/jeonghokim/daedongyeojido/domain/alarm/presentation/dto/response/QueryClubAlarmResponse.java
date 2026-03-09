package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import java.util.List;

public record QueryClubAlarmResponse(List<AlarmResponse> alarms) {

    public static QueryClubAlarmResponse from(List<AlarmResponse> alarms) {
        return new QueryClubAlarmResponse(alarms);
    }
}
