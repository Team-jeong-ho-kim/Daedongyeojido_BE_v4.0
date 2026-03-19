package team.jeonghokim.daedongyeojido.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.LoginResponse;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.response.TokenResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherNotFoundException;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherPasswordMismatchException;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Division;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.global.security.auth.PrincipalDetailsService;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class TeacherLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    @Transactional
    public LoginResponse execute(LoginRequest request) {
        Teacher teacher = teacherRepository.findByAccountId(request.accountId())
                .orElseThrow(() -> TeacherNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(request.password(), teacher.getPassword())) {
            throw TeacherPasswordMismatchException.EXCEPTION;
        }

        TokenResponse tokenResponse = jwtTokenProvider.receiveToken(
                principalDetailsService.createPrincipalKey(Division.TEACHER, teacher.getAccountId())
        );

        return LoginResponse.builder()
                .refreshToken(tokenResponse.refreshToken())
                .accessToken(tokenResponse.accessToken())
                .classNumber(null)
                .userName(teacher.getTeacherName())
                .role(Role.TEACHER)
                .build();
    }
}
