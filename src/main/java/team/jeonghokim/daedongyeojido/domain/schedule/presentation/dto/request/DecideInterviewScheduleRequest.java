package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DecideInterviewScheduleRequest(
        LocalDate interviewSchedule,
        String place,
        LocalDateTime interviewTime
) {
}
