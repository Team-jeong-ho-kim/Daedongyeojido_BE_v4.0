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
    CLUB_CREATION_ACCEPTED("%s 개설 성공", "%s 동아리가 성공적으로 개설되었습니다.");


    private final String title;
    private final String content;

    public String format(Object... args) {
        return content.formatted(args);
    }
}
