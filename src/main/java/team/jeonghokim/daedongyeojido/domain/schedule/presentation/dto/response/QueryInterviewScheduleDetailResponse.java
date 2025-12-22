package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record QueryInterviewScheduleDetailResponse(
        String userName,
        String classNumber,
        Major major,
        String place,
        LocalTime interviewTime,
        LocalDate interviewSchedule
) {

    public static QueryInterviewScheduleDetailResponse of(Schedule schedule, Submission submission) {
        return QueryInterviewScheduleDetailResponse.builder()
                .userName(schedule.getApplicant().getUserName())
                .classNumber(schedule.getApplicant().getClassNumber())
                .major(submission.getMajor())
                .place(schedule.getPlace())
                .interviewTime(schedule.getInterviewTime())
                .interviewSchedule(schedule.getInterviewSchedule())
                .build();
    }
}
