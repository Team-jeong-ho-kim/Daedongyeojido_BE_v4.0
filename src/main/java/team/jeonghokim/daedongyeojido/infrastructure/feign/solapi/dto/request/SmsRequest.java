package team.jeonghokim.daedongyeojido.infrastructure.feign.solapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsRequest {
    private String to;
    private String from;
    private String text;
}
