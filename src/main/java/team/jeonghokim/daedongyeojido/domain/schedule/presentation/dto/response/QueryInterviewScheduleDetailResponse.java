package team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.submission.domain.Submission;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDate;
import java.time.LocalTime;

public record QueryInterviewScheduleDetailResponse(
        String userName,
        String classNumber,
        Major major,
        String place,
        LocalTime interviewTime,
        LocalDate interviewSchedule
) {

    public static QueryInterviewScheduleDetailResponse of(Schedule schedule, Submission submission) {
        return new QueryInterviewScheduleDetailResponse(
                schedule.getApplicant().getUserName(),
                schedule.getApplicant().getClassNumber(),
                submission.getMajor(),
                schedule.getPlace(),
                schedule.getInterviewTime(),
                schedule.getInterviewSchedule()
        );
    }
}
