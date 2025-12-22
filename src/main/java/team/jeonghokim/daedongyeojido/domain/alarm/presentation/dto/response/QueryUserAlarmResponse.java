package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryUserAlarmResponse(List<AlarmResponse> alarms) {

    public static QueryUserAlarmResponse from(List<AlarmResponse> alarms) {
        return QueryUserAlarmResponse.builder()
                .alarms(alarms)
                .build();
    }
}
