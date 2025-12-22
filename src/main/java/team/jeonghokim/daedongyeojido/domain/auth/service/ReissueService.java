package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.domain.repository.RefreshTokenRepository;
import team.jeonghokim.daedongyeojido.domain.auth.exception.RefreshTokenNotFoundException;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.global.security.exception.InvalidTokenException;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtProperties;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponse execute(String refreshToken) {

        if (jwtTokenProvider.isNotRefreshToken(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(token -> {
                    String accountId = token.getAccountId();
                    TokenResponse tokenResponse = jwtTokenProvider.receiveToken(accountId);

                    token.update(tokenResponse.refreshToken(), jwtProperties.getRefreshExpiration());
                    return tokenResponse;
                })
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);
    }
}
