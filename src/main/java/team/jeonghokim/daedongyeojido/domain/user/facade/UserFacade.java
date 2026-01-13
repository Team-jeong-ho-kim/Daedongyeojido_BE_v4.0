package team.jeonghokim.daedongyeojido.domain.user.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.admin.domain.Admin;
import team.jeonghokim.daedongyeojido.domain.admin.domain.repository.AdminRepository;
import team.jeonghokim.daedongyeojido.domain.admin.exeption.AdminNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public User getCurrentUser() {

        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public Admin getCurrentAdmin() {

        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        return adminRepository.findByAccountId(accountId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
