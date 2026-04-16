package team.jeonghokim.daedongyeojido.domain.onepager.domain.enums;

import lombok.Getter;

@Getter
public enum OnePagerDuration {
    DATE("마감날짜 설정"),
    INDEFINITE("무기한");

    private final String onePagerDurationType;

    OnePagerDuration(String onePagerDuration) {
        this.onePagerDurationType = onePagerDuration;
    }
}
