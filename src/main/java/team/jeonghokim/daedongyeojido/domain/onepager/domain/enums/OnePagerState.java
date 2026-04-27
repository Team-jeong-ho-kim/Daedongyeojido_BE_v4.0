package team.jeonghokim.daedongyeojido.domain.onepager.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OnePagerState {
    APPROVED("승인됨"),
    REJECTED("거절됨"),
    SUBMITTED("제출됨"),
    CANCELED("취소됨");

    @JsonValue
    private final String description;
}
