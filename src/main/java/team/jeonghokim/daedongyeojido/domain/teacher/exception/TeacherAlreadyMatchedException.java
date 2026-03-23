package team.jeonghokim.daedongyeojido.domain.teacher.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class TeacherAlreadyMatchedException extends DaedongException {

    public static final DaedongException EXCEPTION = new TeacherAlreadyMatchedException();

    private TeacherAlreadyMatchedException() {
        super(ErrorCode.TEACHER_ALREADY_MATCHED);
    }
}
