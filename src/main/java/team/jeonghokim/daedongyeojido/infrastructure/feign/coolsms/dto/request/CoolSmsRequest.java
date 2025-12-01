package team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.request;

import lombok.Getter;

@Getter
public class CoolSmsRequest {
    String to;
    String from;
    String text;
}
