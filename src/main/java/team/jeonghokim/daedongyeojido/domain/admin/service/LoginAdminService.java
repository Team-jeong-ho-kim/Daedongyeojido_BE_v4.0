package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.domain.Admin;
import team.jeonghokim.daedongyeojido.domain.admin.domain.repository.AdminRepository;
import team.jeonghokim.daedongyeojido.domain.admin.exeption.AdminNotFoundException;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.LoginAdminRequest;
import team.jeonghokim.daedongyeojido.global.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class LoginAdminService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminRepository adminRepository;

    @Transactional
    public void execute(LoginAdminRequest request) {

        Admin admin = adminRepository.findByAccountId(request.accountId())
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        jwtTokenProvider.receiveToken(admin.getAccountId());
    }
}
