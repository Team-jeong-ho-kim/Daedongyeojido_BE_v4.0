package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response;

import java.util.List;

public record QueryInterviewScheduleListResponse(List<InterviewScheduleResponse> schedules) {

    public static QueryInterviewScheduleListResponse from(List<InterviewScheduleResponse> schedules) {
        return new QueryInterviewScheduleListResponse(schedules);
    }
}
