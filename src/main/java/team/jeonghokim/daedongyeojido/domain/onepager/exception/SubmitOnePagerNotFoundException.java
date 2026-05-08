package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class SubmitOnePagerNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new SubmitOnePagerNotFoundException();

    private SubmitOnePagerNotFoundException() {
        super(ErrorCode.SUBMIT_ONE_PAGER_NOT_FOUND);
    }
}
