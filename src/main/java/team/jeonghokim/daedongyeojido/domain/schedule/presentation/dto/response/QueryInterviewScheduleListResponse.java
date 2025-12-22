package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryInterviewScheduleListResponse(List<InterviewScheduleResponse> schedules) {

    public static QueryInterviewScheduleListResponse from(List<InterviewScheduleResponse> schedules) {
        return QueryInterviewScheduleListResponse.builder()
                .schedules(schedules)
                .build();
    }
}
