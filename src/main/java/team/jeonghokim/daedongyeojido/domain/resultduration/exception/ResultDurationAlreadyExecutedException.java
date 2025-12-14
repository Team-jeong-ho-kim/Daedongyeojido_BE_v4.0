package team.jeonghokim.daedongyeojido.domain.resultduration.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ResultDurationAlreadyExecutedException extends DaedongException {
    public static final DaedongException EXCEPTION = new ResultDurationAlreadyExecutedException();

    private ResultDurationAlreadyExecutedException() {
        super(ErrorCode.RESULT_DURATION_ALREADY_EXECUTED);
    }
}
