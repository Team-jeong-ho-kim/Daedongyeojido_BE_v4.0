package team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoolSmsRequest {
    private String to;
    private String from;
    private String text;
}
