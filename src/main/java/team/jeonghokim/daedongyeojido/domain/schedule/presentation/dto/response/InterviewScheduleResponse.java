package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;

public record InterviewScheduleResponse(
        Long scheduleId,
        String userName,
        String classNumber,
        Major major,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate interviewSchedule
) {

    @QueryProjection
    public InterviewScheduleResponse(
            Long scheduleId,
            String userName,
            String classNumber,
            Major major,
            LocalDate interviewSchedule
    ) {
        this.scheduleId = scheduleId;
        this.userName = userName;
        this.classNumber = classNumber;
        this.major = major;
        this.interviewSchedule = interviewSchedule;
    }
}
