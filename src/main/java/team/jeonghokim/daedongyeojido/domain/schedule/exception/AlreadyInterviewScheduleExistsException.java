package team.jeonghokim.daedongyeojido.domain.schedule.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyInterviewScheduleExistsException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyInterviewScheduleExistsException();

    private AlreadyInterviewScheduleExistsException() {
        super(ErrorCode.ALREADY_INTERVIEW_SCHEDULE_EXISTS);
    }
}