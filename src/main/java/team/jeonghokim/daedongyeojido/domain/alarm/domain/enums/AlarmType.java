package team.jeonghokim.daedongyeojido.domain.alarm.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AlarmType {

    CREATE_CLUB_APPLY("%s 개설 신청", "%s의 동아리 개설 신청이 접수되었습니다"),
    DISSOLVE_CLUB_APPLY("%s 해체 신청", "%s의 동아리 해체 신청이 접수되었습니다."),
    CLUB_FINAL_ACCEPTED("%s 최종 합격", "축하드립니다! %s 동아리에 최종 합격하였습니다."),
    CLUB_FINAL_REJECTED("%s 최종 불합격", "아쉽지만 지원자님은 %s 동아리에 불합격하셨습니다."),
    CLUB_MEMBER_APPLY("%s 동아리원 합류 신청", "%s 동아리에 동아리원으로 합류 신청이 도착하였습니다. 합류 / 거절 여부를 결정해주세요."),
    CLUB_CREATION_ACCEPTED("%s 개설 성공", "%s 동아리가 성공적으로 개설되었습니다."),
    CLUB_CREATION_REJECTED("%s 개설 실패", "%s 동아리의 개설 신청이 거부되었습니다."),
    CLUB_DISSOLUTION_ACCEPTED("%s 해체 성공", "%s 동아리가 성공적으로 해체되었습니다."),
    CLUB_DISSOLUTION_REJECTED("%s 해체 실패", "%s 동아리의 해체 신청이 거부되었습니다."),
    USER_JOINED_CLUB("%s님 동아리 합류", "%s님이 동아리에 합류하였습니다."),
    USER_REFUSED_CLUB("%s 동아리 합류 거절", "%s님이 동아리 합류를 거부하였습니다."),
    USER_SUBMIT_APPLICATION("%s 지원서 제출", "%s님이 동아리 지원서를 제출했습니다."),
    USER_CANCEL_APPLICATION("%s 제출 취소", "%s님이 동아리 지원서 제출을 취소하였습니다."),
    DELETE_CLUB_MEMBER("%s 활동 종료", "%s 동아리에서 활동이 종료되었습니다."),
    INTERVIEW_SCHEDULE_CREATED("%s 면접 일정 안내", "축하합니다. 지원자님은 %s 동아리 서류에 합격하셨습니다. 면접이 %s %s에 %s에서 진행됩니다.");

    private final String title;
    private final String content;

    public String formatTitle(Object... args) {
        return title.formatted(args);
    }

    public String formatContent(Object... args) {
        return content.formatted(args);
    }
}
