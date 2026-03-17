package team.jeonghokim.daedongyeojido.domain.clubcreation.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubCreationApplicationAccessDeniedException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubCreationApplicationAccessDeniedException();

    private ClubCreationApplicationAccessDeniedException() {
        super(ErrorCode.CLUB_CREATION_APPLICATION_ACCESS_DENIED);
    }
}
