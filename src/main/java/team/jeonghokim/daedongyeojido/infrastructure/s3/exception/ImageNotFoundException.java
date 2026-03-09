package team.jeonghokim.daedongyeojido.infrastructure.s3.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class ImageNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ImageNotFoundException();

    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
