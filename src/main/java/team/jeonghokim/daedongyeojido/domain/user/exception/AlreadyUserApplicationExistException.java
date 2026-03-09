package team.jeonghokim.daedongyeojido.domain.user.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyUserApplicationExistException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyUserApplicationExistException();

    private AlreadyUserApplicationExistException() {
        super(ErrorCode.ALREADY_USER_APPLICATION_EXIST);
    }
}
