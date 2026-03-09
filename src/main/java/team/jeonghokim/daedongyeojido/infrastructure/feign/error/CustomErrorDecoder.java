package team.jeonghokim.daedongyeojido.infrastructure.feign.error;

import feign.codec.ErrorDecoder;
import feign.Response;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.infrastructure.feign.exception.FeignBadRequestException;
import team.jeonghokim.daedongyeojido.infrastructure.feign.exception.FeignForbiddenException;
import team.jeonghokim.daedongyeojido.infrastructure.feign.exception.FeignUnauthorizedException;

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
            case 404:
                return UserNotFoundException.EXCEPTION;
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }
}
