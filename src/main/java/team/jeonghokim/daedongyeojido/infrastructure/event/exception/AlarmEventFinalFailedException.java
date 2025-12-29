package team.jeonghokim.daedongyeojido.infrastructure.event.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlarmEventFinalFailedException extends DaedongException {
    public static final DaedongException EXCEPTION = new AlarmEventFinalFailedException();

    public AlarmEventFinalFailedException() {
        super(ErrorCode.ALARM_EVENT_FINAL_FAILED);
    }
}
