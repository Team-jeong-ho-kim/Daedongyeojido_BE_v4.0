package team.jeonghokim.daedongyeojido.domain.user.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class UserPhoneNumberAlreadyExistsException extends DaedongException {

    public static final DaedongException EXCEPTION = new UserPhoneNumberAlreadyExistsException();

    private UserPhoneNumberAlreadyExistsException() {
        super(ErrorCode.USER_PHONE_NUMBER_ALREADY_EXISTS);
    }
}
