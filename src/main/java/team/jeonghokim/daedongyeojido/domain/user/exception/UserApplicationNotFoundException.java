package team.jeonghokim.daedongyeojido.domain.user.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class UserApplicationNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new UserApplicationNotFoundException();

    private UserApplicationNotFoundException() {
        super(ErrorCode);
    }
}
