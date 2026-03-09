package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubNotFoundException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubNotFoundException();

    private ClubNotFoundException() {
        super(ErrorCode.CLUB_NOT_FOUND);
    }
}