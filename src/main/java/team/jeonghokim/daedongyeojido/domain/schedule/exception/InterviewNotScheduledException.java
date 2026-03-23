package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InterviewNotScheduledException extends DaedongException {

    public static final DaedongException EXCEPTION = new InterviewNotScheduledException();

    private InterviewNotScheduledException() {
        super(ErrorCode.INTERVIEW_NOT_SCHEDULED);
    }
}
