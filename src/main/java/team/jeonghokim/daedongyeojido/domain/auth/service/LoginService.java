package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.LoginResponse;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;
import team.jeonghokim.daedongyeojido.infrastructure.feign.client.XquareClient;
import team.jeonghokim.daedongyeojido.infrastructure.feign.client.dto.XquareLoginRequest;
import team.jeonghokim.daedongyeojido.infrastructure.feign.client.dto.XquareResponse;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final XquareClient xquareClient;

    @Transactional
    public LoginResponse execute(LoginRequest request) {
        XquareLoginRequest xquareLoginRequest = new XquareLoginRequest(
                request.accountId(),
                request.password()
        );

        XquareResponse xquareUser = xquareClient.getUser(xquareLoginRequest);

        if (xquareUser == null || xquareUser.accountId() == null) {
            throw UserNotFoundException.EXCEPTION;
        }

        User user = userRepository.findByAccountId(xquareUser.accountId())
                .map(existingUser -> coverUserInfo(existingUser, xquareUser))
                .orElseGet(() -> createUser(xquareUser));

        TokenResponse tokenResponse = jwtTokenProvider.receiveToken(user.getAccountId());

        return LoginResponse.builder()
                .refreshToken(tokenResponse.refreshToken())
                .accessToken(tokenResponse.accessToken())
                .classNumber(user.getClassNumber())
                .userName(user.getUserName())
                .build();
    }

    private User createUser(XquareResponse xquareUser) {
        String classNumber = classNumber(
                xquareUser.grade(),
                xquareUser.classNum(),
                xquareUser.num()
        );

        return userRepository.save(
                User.builder()
                        .accountId(xquareUser.accountId())
                        .password(passwordEncoder.encode(xquareUser.password()))
                        .userName(xquareUser.name())
                        .classNumber(classNumber)
                        .role(Role.STUDENT)
                        .build()
        );
    }

    private User coverUserInfo(User user, XquareResponse xquareUser) {
        String classNumber = classNumber(
                xquareUser.grade(),
                xquareUser.classNum(),
                xquareUser.num()
        );

        user.coverInfo(
                xquareUser.name(),
                classNumber
        );
        return user;
    }

    private String classNumber(int grade, int classNum, int num) {
        return String.format("%d%d%02d", grade, classNum, num);
    }
}
