package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class OnePagerInvalidException extends DaedongException {
    public static final DaedongException EXCEPTION = new OnePagerInvalidException();

    private OnePagerInvalidException() {
        super(ErrorCode.ONE_PAGER_INVALID);
    }
}
