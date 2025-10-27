package team.jeonghokim.daedongyeojido.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DaedongException extends RuntimeException {
    private final ErrorCode errorCode;
}
