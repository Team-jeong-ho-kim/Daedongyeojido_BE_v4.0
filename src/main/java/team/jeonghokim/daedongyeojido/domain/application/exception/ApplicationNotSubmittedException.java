package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ApplicationNotSubmittedException extends DaedongException {
    public static final DaedongException EXCEPTION = new ApplicationNotSubmittedException();

    private ApplicationNotSubmittedException() {
        super(ErrorCode.APPLICATION_NOT_SUBMITTED);
    }
}

