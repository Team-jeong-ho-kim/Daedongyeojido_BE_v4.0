package team.jeonghokim.daedongyeojido.domain.clubcreationform.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyClubCreationFormExistsException extends DaedongException {
    public static final DaedongException EXCEPTION = new AlreadyClubCreationFormExistsException();

    private AlreadyClubCreationFormExistsException() {
        super(ErrorCode.ALREADY_CLUB_CREATION_FORM_EXISTS);
    }
}
