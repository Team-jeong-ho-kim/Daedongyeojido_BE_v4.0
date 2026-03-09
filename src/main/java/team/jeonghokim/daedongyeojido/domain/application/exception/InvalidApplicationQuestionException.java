package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InvalidApplicationQuestionException extends DaedongException {
    public static final DaedongException EXCEPTION = new InvalidApplicationQuestionException();

    private InvalidApplicationQuestionException() {
        super(ErrorCode.INVALID_APPLICATION_QUESTION);
    }
}
