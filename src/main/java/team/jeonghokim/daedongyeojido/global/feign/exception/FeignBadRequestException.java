package team.jeonghokim.daedongyeojido.global.feign.exception;


import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class FeignBadRequestException extends DaedongException {

    public static final DaedongException EXCEPTION = new FeignBadRequestException();

    private FeignBadRequestException(){
        super(ErrorCode.FEIGN_BAD_REQUEST);
    }
}
