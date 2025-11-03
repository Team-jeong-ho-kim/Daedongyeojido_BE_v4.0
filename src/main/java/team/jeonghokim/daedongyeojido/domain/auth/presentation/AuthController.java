package team.jeonghokim.daedongyeojido.domain.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.auth.service.LoginService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }
}
