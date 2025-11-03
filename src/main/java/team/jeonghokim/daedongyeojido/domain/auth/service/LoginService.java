package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;
import team.jeonghokim.daedongyeojido.global.xquare.XquareClient;
import team.jeonghokim.daedongyeojido.global.xquare.dto.XquareResponse;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final XquareClient xquareClient;

    @Transactional
    public TokenResponse execute(LoginRequest request) {
        XquareResponse xquareUser = xquareClient.getUser(request);

        if (xquareUser == null || xquareUser.accountId() == null) {
            throw UserNotFoundException.EXCEPTION;
        }

        return jwtTokenProvider.receiveToken(xquareUser.accountId());
    }
}
