package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class UserNotInClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new UserNotInClubException();

    private UserNotInClubException() {
        super(ErrorCode.USER_NOT_IN_CLUB);
    }
}