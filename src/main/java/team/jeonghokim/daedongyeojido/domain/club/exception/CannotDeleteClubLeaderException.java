package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotDeleteClubLeaderException extends DaedongException {
    public static final DaedongException EXCEPTION = new CannotDeleteClubLeaderException();

    private CannotDeleteClubLeaderException() {
        super(ErrorCode.CANNOT_DELETE_CLUB_LEADER);
    }
}
