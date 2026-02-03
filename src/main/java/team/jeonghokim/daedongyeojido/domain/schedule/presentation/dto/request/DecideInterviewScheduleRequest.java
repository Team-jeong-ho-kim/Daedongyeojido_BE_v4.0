package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DecideInterviewScheduleRequest(

        @NotNull(message = "면접 날짜는 필수입니다.")
        @FutureOrPresent(message = "면접 날짜는 과거일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate interviewSchedule,

        @NotNull(message = "면접 장소는 필수입니다.")
        String place,

        @NotNull(message = "면접 시간은 필수입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime interviewTime
) {
}
