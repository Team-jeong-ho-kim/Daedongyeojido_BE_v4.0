package team.jeonghokim.daedongyeojido.global.feign.exception;


import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FeignForbiddenException extends DaedongException {

    public static final DaedongException EXCEPTION = new FeignForbiddenException();

    private FeignForbiddenException(){
        super(ErrorCode.FEIGN_FORBIDDEN_EXCEPTION);
    }
}
