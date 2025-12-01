package team.jeonghokim.daedongyeojido.infrastructure.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.CoolSmsClient;
import team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.request.CoolSmsRequest;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final CoolSmsClient coolSmsClient;

    @Value("${cool-sms.from}")
    private String from;

    public void send(String phoneNumber, Message message) {

        CoolSmsRequest request = new CoolSmsRequest(phoneNumber, from, message.getContent());

        coolSmsClient.send(request);
    }
}
