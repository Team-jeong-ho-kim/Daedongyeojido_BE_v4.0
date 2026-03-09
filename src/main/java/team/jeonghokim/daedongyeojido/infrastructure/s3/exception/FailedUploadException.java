package team.jeonghokim.daedongyeojido.infrastructure.s3.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FailedUploadException extends DaedongException {
    public static final DaedongException EXCEPTION = new FailedUploadException();

    private FailedUploadException() {
        super(ErrorCode.FAILED_UPLOAD);
    }
}
