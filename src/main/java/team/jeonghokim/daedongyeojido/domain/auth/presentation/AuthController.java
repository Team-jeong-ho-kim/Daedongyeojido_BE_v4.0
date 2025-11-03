package team.jeonghokim.daedongyeojido.domain.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.auth.service.LoginService;
import team.jeonghokim.daedongyeojido.domain.auth.service.LogoutService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;
    private final LogoutService logoutService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        logoutService.execute();
    }
}
