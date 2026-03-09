package team.jeonghokim.daedongyeojido.domain.resultduration.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ResultDurationNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ResultDurationNotFoundException();

    private ResultDurationNotFoundException() {
        super(ErrorCode.RESULT_DURATION_NOT_FOUND);
    }
}
