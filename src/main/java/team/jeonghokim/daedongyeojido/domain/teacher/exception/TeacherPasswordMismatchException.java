package team.jeonghokim.daedongyeojido.domain.teacher.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class TeacherPasswordMismatchException extends DaedongException {

    public static final DaedongException EXCEPTION = new TeacherPasswordMismatchException();

    private TeacherPasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
