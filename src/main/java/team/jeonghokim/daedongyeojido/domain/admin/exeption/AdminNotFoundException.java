package team.jeonghokim.daedongyeojido.domain.admin.exeption;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class AdminNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new AdminNotFoundException();

    private AdminNotFoundException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}
