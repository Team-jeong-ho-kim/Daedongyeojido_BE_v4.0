package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyApplyClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyApplyClubException();

    private AlreadyApplyClubException() {
        super(ErrorCode.ALREADY_APPLY_CLUB);
    }
}
