package team.jeonghokim.daedongyeojido.infrastructure.sms.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    INTERVIEW_SCHEDULE_DECIDED(
            "면접 일정 안내",
            "%s 동아리 면접 일정이 확정되었습니다.\n대동여지도 알림페이지에서 확인해 주세요."
    ),
    INTERVIEW_SCHEDULE_CHANGED(
            "면접 일정 변경",
            "%s 동아리 면접 일정이 변경되었습니다.\n대동여지도 알림페이지에서 확인해 주세요."
    ),
    CLUB_FINAL_ACCEPTED(
            "동아리 최종 합격",
            "축하합니다! %s 동아리 최종 합격입니다.\n대동여지도 알림에서 동아리 합류 여부를 선택해 주세요."
    ),
    CLUB_FINAL_REJECTED(
            "동아리 최종 불합격",
            "%s 동아리 최종 결과는 불합격입니다.\n지원해 주셔서 감사합니다."
    );

    private final String title;
    private final String content;

    public String format(Object... args) {

        return content.formatted(args);
    }
}
