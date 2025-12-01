package team.jeonghokim.daedongyeojido.infrastructure.feign.xquare;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.infrastructure.feign.xquare.dto.response.XquareResponse;

@FeignClient(name = "xquare-login", url = "${das-key.login-api-url}")
public interface XquareClient {

    @GetMapping("/user-data")
    XquareResponse getUser(@RequestBody LoginRequest request);
}
