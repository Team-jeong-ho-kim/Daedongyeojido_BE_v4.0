package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InterviewScheduleAccessDeniedException extends DaedongException {

    public static final DaedongException EXCEPTION = new InterviewScheduleAccessDeniedException();

    private InterviewScheduleAccessDeniedException() {
        super(ErrorCode.INTERVIEW_SCHEDULE_ACCESS_DENIED);
    }
}
