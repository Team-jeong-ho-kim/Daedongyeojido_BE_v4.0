package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InterviewNotCompletedException extends DaedongException {

    public static final DaedongException EXCEPTION = new InterviewNotCompletedException();

    private InterviewNotCompletedException() {
        super(ErrorCode.INTERVIEW_NOT_COMPLETED);
    }
}
