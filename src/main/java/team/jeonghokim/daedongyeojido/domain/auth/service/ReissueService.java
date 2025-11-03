package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.auth.domain.RefreshToken;
import team.jeonghokim.daedongyeojido.domain.auth.domain.repository.RefreshTokenRepository;
import team.jeonghokim.daedongyeojido.domain.auth.exception.RefreshTokenNotFoundException;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.ReissueRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(ReissueRequest request) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.token())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        return jwtTokenProvider.receiveToken(refreshToken.getAccountId());
    }
}
