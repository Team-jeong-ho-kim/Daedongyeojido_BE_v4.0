package team.jeonghokim.daedongyeojido.domain.resultduration.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ResultDurationAlreadySetException extends DaedongException {
    public static final DaedongException EXCEPTION = new ResultDurationAlreadySetException();

    private ResultDurationAlreadySetException() {
        super(ErrorCode.RESULT_DURATION_ALREADY_SET);
    }
}
