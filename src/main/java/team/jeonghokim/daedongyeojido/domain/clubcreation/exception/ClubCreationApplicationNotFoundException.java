package team.jeonghokim.daedongyeojido.domain.clubcreation.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubCreationApplicationNotFoundException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubCreationApplicationNotFoundException();

    private ClubCreationApplicationNotFoundException() {
        super(ErrorCode.CLUB_CREATION_APPLICATION_NOT_FOUND);
    }
}
