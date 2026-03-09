package team.jeonghokim.daedongyeojido.domain.alarm.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlarmNotFoundException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlarmNotFoundException();

    private AlarmNotFoundException() {
        super(ErrorCode.ALARM_NOT_FOUND);
    }
}
