package team.jeonghokim.daedongyeojido.infrastructure.s3.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InvalidExtensionException extends DaedongException {
    public static final DaedongException EXCEPTION = new InvalidExtensionException();

    private InvalidExtensionException() {
        super(ErrorCode.INVALID_EXTENSION);
    }
}
