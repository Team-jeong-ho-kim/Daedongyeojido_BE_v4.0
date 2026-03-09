package team.jeonghokim.daedongyeojido.domain.announcement.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AnnouncementAccessDeniedException extends DaedongException {

    public static final DaedongException EXCEPTION = new AnnouncementAccessDeniedException();

    private AnnouncementAccessDeniedException() {
        super(ErrorCode.ANNOUNCEMENT_ACCESS_DENIED);
    }
}
