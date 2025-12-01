package team.jeonghokim.daedongyeojido.global.solapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsRequest {
    private Message message;

    @Getter
    @AllArgsConstructor
    public static class Message {
        private String to;
        private String from;
        private String text;
    }
}
