package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ApplicationAccessDeniedException extends DaedongException {
    public static final DaedongException EXCEPTION = new ApplicationAccessDeniedException();

    private ApplicationAccessDeniedException() {
        super(ErrorCode.APPLICATION_ACCESS_DENIED);
    }
}
