package team.jeonghokim.daedongyeojido.domain.clubcreation.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class CannotModifyClubCreationApplicationException extends DaedongException {

    public static final DaedongException EXCEPTION = new CannotModifyClubCreationApplicationException();

    private CannotModifyClubCreationApplicationException() {
        super(ErrorCode.CANNOT_MODIFY_CLUB_CREATION_APPLICATION);
    }
}
