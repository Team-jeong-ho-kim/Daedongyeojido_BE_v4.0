package team.jeonghokim.daedongyeojido.domain.alarm.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlarmAccessDeniedException extends DaedongException {
    public static final DaedongException EXCEPTION = new AlarmAccessDeniedException();

    private AlarmAccessDeniedException() {
        super(ErrorCode.ALARM_ACCESS_DENIED);
    }
}
