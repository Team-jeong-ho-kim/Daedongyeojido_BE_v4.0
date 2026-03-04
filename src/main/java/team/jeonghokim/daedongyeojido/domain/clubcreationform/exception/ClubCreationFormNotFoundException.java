package team.jeonghokim.daedongyeojido.domain.clubcreationform.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubCreationFormNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ClubCreationFormNotFoundException();

    private ClubCreationFormNotFoundException() {
        super(ErrorCode.CLUB_CREATION_FORM_NOT_FOUND);
    }
}
