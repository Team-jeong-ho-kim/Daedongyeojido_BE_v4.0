package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ApplicationNotAcceptedException extends DaedongException {
    public static final ApplicationNotAcceptedException EXCEPTION = new ApplicationNotAcceptedException();

    private ApplicationNotAcceptedException() {
        super(ErrorCode.APPLICATION_NOT_ACCEPTED);
    }
}
