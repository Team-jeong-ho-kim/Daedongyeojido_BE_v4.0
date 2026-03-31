package team.jeonghokim.daedongyeojido.infrastructure.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.infrastructure.feign.client.dto.XquareLoginRequest;
import team.jeonghokim.daedongyeojido.infrastructure.feign.client.dto.XquareResponse;

@FeignClient(name = "xquare-login", url = "${key.login-api-url}")
public interface XquareClient {

    @PostMapping("/dsm-login/user/user-data")
    XquareResponse getUser(@RequestBody XquareLoginRequest request);
}
