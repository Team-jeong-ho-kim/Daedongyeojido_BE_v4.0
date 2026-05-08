package team.jeonghokim.daedongyeojido.domain.onepager.domain.enums;

public enum OnePagerState {
    APPROVED("승인됨"),
    REJECTED("반려됨"),
    SUBMITTED("제출됨"),
    CANCELED("취소됨");

    public final String displayState;

    OnePagerState(String displayState) {
        this.displayState = displayState;
    }
}
