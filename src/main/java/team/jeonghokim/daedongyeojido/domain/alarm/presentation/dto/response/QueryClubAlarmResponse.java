package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryClubAlarmResponse(List<AlarmResponse> alarms) {

    public static QueryClubAlarmResponse from(List<AlarmResponse> alarms) {
        return QueryClubAlarmResponse.builder()
                .alarms(alarms)
                .build();
    }
}
