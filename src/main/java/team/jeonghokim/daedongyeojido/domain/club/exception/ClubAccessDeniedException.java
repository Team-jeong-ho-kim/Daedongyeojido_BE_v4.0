package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubAccessDeniedException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubAccessDeniedException();

    private ClubAccessDeniedException() {
        super(ErrorCode.CLUB_ACCESS_DENIED);
    }
}