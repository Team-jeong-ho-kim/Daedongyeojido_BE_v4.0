package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubApplicationNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ClubApplicationNotFoundException();

    private ClubApplicationNotFoundException() {
        super(ErrorCode.CLUB_APPLICATION_NOT_FOUND);
    }
}
