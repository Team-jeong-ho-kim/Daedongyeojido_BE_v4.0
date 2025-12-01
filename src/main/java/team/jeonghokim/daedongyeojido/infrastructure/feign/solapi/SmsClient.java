package team.jeonghokim.daedongyeojido.infrastructure.feign.solapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.infrastructure.feign.solapi.dto.request.SmsRequest;
import team.jeonghokim.daedongyeojido.infrastructure.feign.solapi.dto.response.SmsResponse;

@FeignClient(
        name = "solapi-sms",
        url = "${solapi-sms.url}"
)
public interface SmsClient {

    @PostMapping("/messages/v4/send")
    SmsResponse send(@RequestBody SmsRequest request);
}
