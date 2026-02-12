package team.jeonghokim.daedongyeojido.domain.alarm.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotDeleteAlarmException extends DaedongException {

    public static final DaedongException EXCEPTION = new CannotDeleteAlarmException();

    private CannotDeleteAlarmException() {
        super(ErrorCode.CANNOT_DELETE_ALARM);
    }
}
