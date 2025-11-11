package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubNotOpenException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubNotOpenException();

    private ClubNotOpenException() {
        super(ErrorCode.CLUB_NOT_OPEN);
    }
}
