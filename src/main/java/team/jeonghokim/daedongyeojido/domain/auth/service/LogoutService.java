package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.auth.domain.RefreshToken;
import team.jeonghokim.daedongyeojido.domain.auth.domain.repository.RefreshTokenRepository;
import team.jeonghokim.daedongyeojido.domain.auth.exception.RefreshTokenNotFoundException;
import team.jeonghokim.daedongyeojido.global.security.auth.DaedongUserDetails;
import team.jeonghokim.daedongyeojido.global.security.exception.InvalidTokenException;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void execute() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof DaedongUserDetails principal)) {
            throw InvalidTokenException.EXCEPTION;
        }

        RefreshToken refreshToken = refreshTokenRepository.findByAccountId(principal.getPrincipalKey())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        refreshTokenRepository.delete(refreshToken);
    }
}
