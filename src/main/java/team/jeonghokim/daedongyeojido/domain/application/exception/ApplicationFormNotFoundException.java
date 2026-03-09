package team.jeonghokim.daedongyeojido.domain.application.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ApplicationFormNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ApplicationFormNotFoundException();

    private ApplicationFormNotFoundException() {
        super(ErrorCode.APPLICATION_FORM_NOT_FOUND);
    }
}
