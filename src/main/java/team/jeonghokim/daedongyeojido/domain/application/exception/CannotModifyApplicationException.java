package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotModifyApplicationException extends DaedongException {
    public static final DaedongException EXCEPTION = new CannotModifyApplicationException();

    private CannotModifyApplicationException() {
        super(ErrorCode.CANNOT_MODIFY_APPLICATION);
    }
}
