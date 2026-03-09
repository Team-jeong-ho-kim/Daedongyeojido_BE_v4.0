package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InterviewScheduleNotFoundException extends DaedongException {

    public static final DaedongException EXCEPTION = new InterviewScheduleNotFoundException();

    private InterviewScheduleNotFoundException() {
        super(ErrorCode.INTERVIEW_SCHEDULE_NOT_FOUND);
    }
}
