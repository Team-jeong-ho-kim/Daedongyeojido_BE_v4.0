package team.jeonghokim.daedongyeojido.infrastructure.event.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class SmsEventFinalFailedException extends DaedongException {

    public SmsEventFinalFailedException(Throwable cause) {
        super(ErrorCode.SMS_EVENT_FINAL_FAILED, cause);
    }
}
