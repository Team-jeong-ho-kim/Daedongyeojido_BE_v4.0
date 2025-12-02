package team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response;

import java.util.List;

public record QueryUserAlarmResponse(List<AlarmResponse> alarmResponses) {
}
