package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InvalidUserException extends DaedongException {
    public static final DaedongException EXCEPTION = new InvalidUserException();

    private InvalidUserException() {
        super(ErrorCode.INVALID_USER);
    }
}
