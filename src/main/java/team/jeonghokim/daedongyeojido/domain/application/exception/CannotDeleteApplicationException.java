package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotDeleteApplicationException extends DaedongException {
    public static final DaedongException EXCEPTION = new CannotDeleteApplicationException();

    private CannotDeleteApplicationException() {
        super(ErrorCode.CANNOT_DELETE_APPLICATION);
    }
}
