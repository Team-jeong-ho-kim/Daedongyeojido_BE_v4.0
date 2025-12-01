package team.jeonghokim.daedongyeojido.infrastructure.feign.solapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsRequest {
    private Message message;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Message {
        private String to;
        private String from;
        private String text;
    }
}
