package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class InvalidSubmitOnePagerException extends DaedongException {
    public static final DaedongException EXCEPTION = new InvalidSubmitOnePagerException();

    private InvalidSubmitOnePagerException() {
        super(ErrorCode.INVALID_SUBMIT_ONE_PAGER);
    }
}
