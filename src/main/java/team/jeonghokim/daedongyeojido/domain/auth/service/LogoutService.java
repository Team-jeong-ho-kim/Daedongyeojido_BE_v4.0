package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.auth.domain.RefreshToken;
import team.jeonghokim.daedongyeojido.domain.auth.domain.repository.RefreshTokenRepository;
import team.jeonghokim.daedongyeojido.domain.auth.exception.RefreshTokenNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final UserFacade userFacade;
    private final RefreshTokenRepository refreshTokenRepository;

    public void execute() {
        User user = userFacade.getCurrentUser();

        RefreshToken refreshToken = refreshTokenRepository.findByAccountId(user.getAccountId())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        refreshTokenRepository.delete(refreshToken);
    }
}
