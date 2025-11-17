package team.jeonghokim.daedongyeojido.domain.announcement.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AnnouncementNotFoundException extends DaedongException {

    public static final DaedongException EXCEPTION = new AnnouncementNotFoundException();

    private AnnouncementNotFoundException() {
        super(ErrorCode.ANNOUNCEMENT_NOT_FOUND);
    }
}
