package team.jeonghokim.daedongyeojido.domain.alarm.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AlarmType {

    DISSOLVE_CLUB("동아리 해체 신청", "%s의 동아리 해체 신청이 접수되었습니다.");

    private final String title;
    private final String content;

    public String format(Object... args) {
        return content.formatted(args);
    }
}
