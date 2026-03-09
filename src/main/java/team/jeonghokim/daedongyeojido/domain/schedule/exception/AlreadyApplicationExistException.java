package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyApplicationExistException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyApplicationExistException();

    private AlreadyApplicationExistException() {
        super(ErrorCode.ALREADY_APPLICATION_EXIST);
    }
}
