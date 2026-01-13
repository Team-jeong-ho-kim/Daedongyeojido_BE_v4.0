package team.jeonghokim.daedongyeojido.domain.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.LoginAdminRequest;
import team.jeonghokim.daedongyeojido.domain.admin.service.LoginAdminService;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.LoginResponse;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.auth.service.LoginUserService;
import team.jeonghokim.daedongyeojido.domain.auth.service.LogoutService;
import team.jeonghokim.daedongyeojido.domain.auth.service.ReissueService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginUserService loginUserService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;
    private final LoginAdminService loginAdminService;

    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return loginUserService.execute(request);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        logoutService.execute();
    }

    @PatchMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse reissue(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return reissueService.execute(refreshToken);
    }

    @PostMapping("/admin/login")
    @ResponseStatus(HttpStatus.OK)
    public void adminLogin(@RequestBody @Valid LoginAdminRequest request) {
        loginAdminService.execute(request);
    }
}
