package team.jeonghokim.daedongyeojido.domain.admin.exeption;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AdminAlreadyExistException extends DaedongException {

    public static final DaedongException EXCEPTION = new AdminAlreadyExistException();

    private AdminAlreadyExistException() {
        super(ErrorCode.ADMIN_ALREADY_EXISTS);
    }
}
