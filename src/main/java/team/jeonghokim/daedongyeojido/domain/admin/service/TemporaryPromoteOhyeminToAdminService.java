package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response.TemporaryPromoteUserRoleResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryPromoteOhyeminToAdminService {

    private final UserRepository userRepository;

    @Transactional
    public TemporaryPromoteUserRoleResponse execute() {
        List<User> users = userRepository.findAllByUserName("오혜민");

        if (users.isEmpty()) {
            throw new IllegalStateException("User '오혜민' not found");
        }

        if (users.size() > 1) {
            throw new IllegalStateException("Multiple users found for '오혜민'");
        }

        User user = users.get(0);
        user.promoteToAdmin();

        return new TemporaryPromoteUserRoleResponse(
                user.getId(),
                user.getAccountId(),
                user.getUserName(),
                user.getRole().name()
        );
    }
}
