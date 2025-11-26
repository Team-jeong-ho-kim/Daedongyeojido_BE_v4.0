package team.jeonghokim.daedongyeojido.domain.club.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AlreadyApplicantInClubException extends DaedongException {

    public static final DaedongException EXCEPTION = new AlreadyApplicantInClubException();

    private AlreadyApplicantInClubException() {
        super(ErrorCode.ALREADY_APPLICANT_IN_CLUB_EXCEPTION);
    }
}