package team.jeonghokim.daedongyeojido.domain.submission.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class SubmissionNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new SubmissionNotFoundException();

    private SubmissionNotFoundException() {
        super(ErrorCode.SUBMISSION_NOT_FOUND);
    }
}
