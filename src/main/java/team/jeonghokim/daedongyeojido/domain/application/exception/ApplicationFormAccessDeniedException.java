package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ApplicationFormAccessDeniedException extends DaedongException {
    public static final DaedongException EXCEPTION = new ApplicationFormAccessDeniedException();

    public  ApplicationFormAccessDeniedException() {
        super(ErrorCode.APPLICATION_FORM_ACCESS_DENIED);
    }
}
