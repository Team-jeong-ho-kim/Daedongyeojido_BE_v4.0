package team.jeonghokim.daedongyeojido.global.feign.exception;


import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FeignUnauthorizedException extends DaedongException {

    public static final DaedongException EXCEPTION = new FeignUnauthorizedException();

    private FeignUnauthorizedException(){
        super(ErrorCode.FEIGN_UNAUTHORIZED_EXCEPTION);
    }
}
