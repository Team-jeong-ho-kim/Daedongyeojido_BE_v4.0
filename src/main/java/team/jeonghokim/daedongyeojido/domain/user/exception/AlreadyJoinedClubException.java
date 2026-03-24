package team.jeonghokim.daedongyeojido.domain.user.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyJoinedClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyJoinedClubException();

    private AlreadyJoinedClubException() {
        super(ErrorCode.ALREADY_JOINED_CLUB);
    }
}
