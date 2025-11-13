package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class DeleteTeamMemberService {

    private final UserRepository userRepository;

    @Transactional
    public void execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        user.leaveClub();
    }
}
