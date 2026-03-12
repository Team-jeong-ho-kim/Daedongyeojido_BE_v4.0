package team.jeonghokim.daedongyeojido.domain.file.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyFileExistsException extends DaedongException {
    public static final DaedongException EXCEPTION = new AlreadyFileExistsException();

    private AlreadyFileExistsException() {
        super(ErrorCode.ALREADY_FILE_EXISTS);
    }
}
