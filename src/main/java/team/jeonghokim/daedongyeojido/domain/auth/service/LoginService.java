package team.jeonghokim.daedongyeojido.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;
import team.jeonghokim.daedongyeojido.global.xquare.XquareClient;
import team.jeonghokim.daedongyeojido.global.xquare.dto.XquareResponse;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final XquareClient xquareClient;

    @Transactional
    public TokenResponse execute(LoginRequest request) {
        XquareResponse xquareUser = xquareClient.getUser(request);

        if (xquareUser == null || xquareUser.accountId() == null) {
            throw UserNotFoundException.EXCEPTION;
        }

        User user = userRepository.findByAccountId(xquareUser.accountId())
                .map(existingUser -> coverUserInfo(existingUser, xquareUser))
                .orElseGet(() -> createUser(xquareUser));


        return jwtTokenProvider.receiveToken(user.getAccountId());
    }

    private User createUser(XquareResponse xquareUser) {
        return userRepository.save(
                User.builder()
                        .accountId(xquareUser.accountId())
                        .password(passwordEncoder.encode(xquareUser.password()))
                        .userName(xquareUser.name())
                        .classNumber(xquareUser.classNum())
                        .role(Role.STUDENT)
                        .build()
        );
    }

    private User coverUserInfo(User user, XquareResponse xquareUser) {
         user.coverInfo(
                xquareUser.name(),
                 xquareUser.accountId(),
                 passwordEncoder.encode(xquareUser.password()),
                 xquareUser.classNum()
        );
         return user;
    }
}
