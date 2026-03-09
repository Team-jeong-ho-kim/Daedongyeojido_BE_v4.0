package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubMisMatchException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubMisMatchException();

    private ClubMisMatchException() {
        super(ErrorCode.CLUB_MISMATCH);
    }
}