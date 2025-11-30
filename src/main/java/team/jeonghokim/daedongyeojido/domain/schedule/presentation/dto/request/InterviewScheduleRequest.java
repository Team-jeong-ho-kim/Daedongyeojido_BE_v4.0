package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record InterviewScheduleRequest(

        @NotNull(message = "면접 날짜는 필수입니다.")
        @FutureOrPresent(message = "면접 날짜는 과거일 수 없습니다.")
        @JsonProperty("interviewSchedule")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate interviewSchedule,

        String place,

        @NotNull(message = "면접 시간은 필수입니다.")
        @JsonProperty("interviewTime")
        @JsonFormat(pattern = "HH:mm")
        LocalTime interviewTime
) {
}
