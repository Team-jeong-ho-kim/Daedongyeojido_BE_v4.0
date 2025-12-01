package team.jeonghokim.daedongyeojido.global.solapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.global.solapi.dto.request.SmsRequest;
import team.jeonghokim.daedongyeojido.global.solapi.dto.response.SmsResponse;
import team.jeonghokim.daedongyeojido.infrastructure.sms.config.SmsConfig;

@FeignClient(
        name = "solapi-sms",
        url = "${solapi-sms.url}",
        configuration = SmsConfig.class
)
public interface SmsClient {

    @PostMapping("/messages/v4/send")
    SmsResponse send(@RequestBody SmsRequest request);
}
