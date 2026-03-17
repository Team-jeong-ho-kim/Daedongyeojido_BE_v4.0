package team.jeonghokim.daedongyeojido.domain.clubcreation.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ClubCreationReviewAccessDeniedException extends DaedongException {

    public static final DaedongException EXCEPTION = new ClubCreationReviewAccessDeniedException();

    private ClubCreationReviewAccessDeniedException() {
        super(ErrorCode.CLUB_CREATION_REVIEW_ACCESS_DENIED);
    }
}
