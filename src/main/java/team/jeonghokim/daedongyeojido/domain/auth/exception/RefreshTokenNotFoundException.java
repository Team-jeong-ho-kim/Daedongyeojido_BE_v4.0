package team.jeonghokim.daedongyeojido.domain.auth.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class RefreshTokenNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new RefreshTokenNotFoundException();

    private RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
