package team.jeonghokim.daedongyeojido.infrastructure.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.global.solapi.SmsClient;
import team.jeonghokim.daedongyeojido.global.solapi.dto.request.SmsRequest;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsClient smsClient;

    @Value("${solapi-sms.from}")
    private String from;

    public void send(String phoneNumber, Message message) {

        SmsRequest request = new SmsRequest(phoneNumber, from, message.getContent());

        smsClient.send(request);
    }
}
