package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.domain.Admin;
import team.jeonghokim.daedongyeojido.domain.admin.domain.repository.AdminRepository;
import team.jeonghokim.daedongyeojido.domain.admin.exeption.AdminAlreadyExistException;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.AdminRequest;

@Service
@RequiredArgsConstructor
public class CreateAdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(AdminRequest request) {

        if (adminRepository.findByAccountId(request.accountId()).isPresent()) {
            throw AdminAlreadyExistException.EXCEPTION;
        }

        adminRepository.save(Admin.builder()
                    .accountId(request.accountId())
                    .password(passwordEncoder.encode(request.password()))
                .build());
    }
}
