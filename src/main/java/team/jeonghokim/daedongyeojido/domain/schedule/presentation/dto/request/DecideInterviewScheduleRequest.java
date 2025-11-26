package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DecideInterviewScheduleRequest(
        @JsonProperty("interviewSchedule")
        LocalDate interviewSchedule,

        String place,

        @JsonProperty("interviewTime")
        LocalDateTime interviewTime
) {
}
