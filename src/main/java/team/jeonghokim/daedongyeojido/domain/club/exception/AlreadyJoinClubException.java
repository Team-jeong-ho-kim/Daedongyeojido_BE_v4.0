package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyJoinClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyJoinClubException();

    private AlreadyJoinClubException() {
        super(ErrorCode.ALREADY_JOIN_CLUB);
    }
}