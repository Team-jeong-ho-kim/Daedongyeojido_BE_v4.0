package team.jeonghokim.daedongyeojido.infrastructure.sms.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    INTERVIEW_SCHEDULE_DECIDED("면접 일정 안내", "%s 동아리 면접 일정이 확정되었습니다. 자세한 면접 일정은 대동여지도에 방문하셔서 마이페이지/지원내역/면접일정조회 에서 확인해주세요."),
    INTERVIEW_SCHEDULE_CHANGED("면접 일정 변경", "%s 동아리 면접 일정이 변경되었습니다. 자세한 변경 사항은 대동여지도에 방문하셔서 마이페이지/지원내역/면접일정조회 에서 확인해주세요."),
    CLUB_FINAL_ACCEPTED("동아리 최종 합격", "축하드립니다! %s 동아리에 최종 합격하였습니다. 대동여지도에 방문하셔서 알림페이지에서 해당 동아리 합류 여부를 결정해주세요!"),
    CLUB_FINAL_REJECTED("동아리 최종 불합격", "아쉽지만 지원자님은 %s 동아리에 불합격하셨습니다.");

    private final String title;
    private final String content;

    public String format(Object... args) {
        return content.formatted(args);
    }
}
