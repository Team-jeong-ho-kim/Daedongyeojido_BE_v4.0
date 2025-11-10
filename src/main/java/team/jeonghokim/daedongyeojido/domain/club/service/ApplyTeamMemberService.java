package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.TeamMemberRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class ApplyTeamMemberService {
    private final UserApplicationRepository userApplicationRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(TeamMemberRequest request) {
        User user = userFacade.getCurrentUser();

        User userApplication = userRepository.findByUserNameAndClassNumber(request.getUserName(), request.getClassNumber())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userApplicationRepository.save(UserApplication.builder()
                .userName(userApplication.getUserName())
                .classNumber(userApplication.getClassNumber())
                .isApproved(false)
                .club(user.getClub())
                .build());
    }
}
