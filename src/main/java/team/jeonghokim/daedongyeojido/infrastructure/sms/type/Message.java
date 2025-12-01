package team.jeonghokim.daedongyeojido.infrastructure.sms.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    CLUB_FINAL_ACCEPTED("동아리 최종 합격", "축하드립니다! 동아리에 최종 합격하였습니다.");

    private final String title;
    private final String defaultMessage;
}
