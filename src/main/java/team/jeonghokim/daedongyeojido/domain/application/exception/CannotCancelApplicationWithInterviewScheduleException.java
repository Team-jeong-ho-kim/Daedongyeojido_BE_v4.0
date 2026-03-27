package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotCancelApplicationWithInterviewScheduleException extends DaedongException {

    public static final DaedongException EXCEPTION = new CannotCancelApplicationWithInterviewScheduleException();

    private CannotCancelApplicationWithInterviewScheduleException() {
        super(ErrorCode.CANNOT_CANCEL_APPLICATION_WITH_INTERVIEW_SCHEDULE);
    }
}
