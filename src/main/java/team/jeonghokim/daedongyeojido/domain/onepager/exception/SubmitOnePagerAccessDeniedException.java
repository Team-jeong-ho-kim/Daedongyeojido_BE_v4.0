package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class SubmitOnePagerAccessDeniedException extends DaedongException {
    public static final DaedongException EXCEPTION = new SubmitOnePagerAccessDeniedException();

    private SubmitOnePagerAccessDeniedException() {
        super(ErrorCode.SUBMIT_ONE_PAGER_ACCESS_DENIED);
    }
}
