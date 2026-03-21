package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InterviewAlreadyCompletedException extends DaedongException {

    public static final DaedongException EXCEPTION = new InterviewAlreadyCompletedException();

    private InterviewAlreadyCompletedException() {
        super(ErrorCode.INTERVIEW_ALREADY_COMPLETED);
    }
}
