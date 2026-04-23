package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InvalidRoleException extends DaedongException {
    public static final DaedongException EXCEPTION = new InvalidRoleException();

    public InvalidRoleException() {
        super(ErrorCode.INVALID_ROLE);
    }

}
