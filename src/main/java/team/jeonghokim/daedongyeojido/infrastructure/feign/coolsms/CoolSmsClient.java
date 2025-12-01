package team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.request.CoolSmsRequest;
import team.jeonghokim.daedongyeojido.infrastructure.feign.coolsms.dto.response.CoolSmsResponse;

@FeignClient(
        name = "solapi-sms",
        url = "${cool-sms.url}"
)
public interface CoolSmsClient {

    @PostMapping("/messages/v4/send")
    CoolSmsResponse send(@RequestBody CoolSmsRequest request);
}
