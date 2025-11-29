package team.jeonghokim.daedongyeojido.domain.user.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadySelectClubException extends DaedongException {
    public static final DaedongException EXCEPTION = new AlreadySelectClubException();

    private AlreadySelectClubException() {
        super(ErrorCode.ALREADY_SELECT_CLUB);
    }
}
