package team.jeonghokim.daedongyeojido.infrastructure.s3.exception;


import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FailedDeleteException extends DaedongException {
    public static final DaedongException EXCEPTION = new FailedDeleteException();

    private FailedDeleteException() {
        super(ErrorCode.FAILED_DELETE);
    }
}
