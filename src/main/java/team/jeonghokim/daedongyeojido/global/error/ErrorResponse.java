package team.jeonghokim.daedongyeojido.global.error;

import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String description
) {
    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return new ErrorResponse(
                errorCode.getErrorMessage(),
                errorCode.getStatusCode(),
                LocalDateTime.now(),
                description
        );
    }

    public static ErrorResponse of(int statusCode, String description) {
        return new ErrorResponse(
                description,
                statusCode,
                LocalDateTime.now(),
                description
        );
    }
}
