package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyExistsClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyExistsClubException();

    private AlreadyExistsClubException() {
        super(ErrorCode.ALREADY_EXISTS_CLUB);
    }
}
