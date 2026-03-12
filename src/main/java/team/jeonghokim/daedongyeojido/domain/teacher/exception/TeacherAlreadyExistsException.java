package team.jeonghokim.daedongyeojido.domain.teacher.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class TeacherAlreadyExistsException extends DaedongException {

    public static final DaedongException EXCEPTION = new TeacherAlreadyExistsException();

    private TeacherAlreadyExistsException() {
        super(ErrorCode.TEACHER_ALREADY_EXISTS);
    }
}
