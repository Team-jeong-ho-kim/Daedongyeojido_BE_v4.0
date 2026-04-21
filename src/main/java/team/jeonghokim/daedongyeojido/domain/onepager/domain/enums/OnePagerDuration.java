package team.jeonghokim.daedongyeojido.domain.onepager.domain.enums;

import lombok.Getter;

@Getter
public enum OnePagerDuration {
    DATE("마감날짜 설정"),
    INDEFINITE("마감일 없음");

    private final String onePagerDurationType;

    OnePagerDuration(String onePagerDuration) {
        this.onePagerDurationType = onePagerDuration;
    }
}
