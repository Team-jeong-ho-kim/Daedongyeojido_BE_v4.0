package team.jeonghokim.daedongyeojido.global.feign;

import feign.codec.ErrorDecoder;
import feign.Response;
import team.jeonghokim.daedongyeojido.global.feign.exception.FeignBadRequestException;
import team.jeonghokim.daedongyeojido.global.feign.exception.FeignForbiddenException;
import team.jeonghokim.daedongyeojido.global.feign.exception.FeignUnauthorizedException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response == null) return defaultDecoder.decode(methodKey, response);

        int status = response.status();
        switch (status) {
            case 400:
                return FeignBadRequestException.EXCEPTION;
            case 401:
                return FeignUnauthorizedException.EXCEPTION;
            case 403:
                return FeignForbiddenException.EXCEPTION;
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }
}
