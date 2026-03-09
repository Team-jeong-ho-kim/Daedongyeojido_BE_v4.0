package team.jeonghokim.daedongyeojido.infrastructure.s3.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FileSizeExceededException extends DaedongException {
    public static final DaedongException EXCEPTION = new FileSizeExceededException();

    private FileSizeExceededException() {
        super(ErrorCode.FILE_SIZE_EXCEEDED);
    }
}
