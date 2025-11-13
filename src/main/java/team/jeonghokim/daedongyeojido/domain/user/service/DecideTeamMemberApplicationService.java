package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.user.exception.UserApplicationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideTeamMemberApplicationRequest;

@Service
@RequiredArgsConstructor
public class DecideTeamMemberApplicationService {
    private final UserFacade userFacade;
    private final UserApplicationRepository userApplicationRepository;

    @Transactional
    public void execute(DecideTeamMemberApplicationRequest request) {
        User user = userFacade.getCurrentUser();

        UserApplication userApplication = userApplicationRepository.findByUserId(user.getId())
                .orElseThrow(() -> UserApplicationNotFoundException.EXCEPTION);

        if (request.getIsApproved()) {
            user.approvedTeamMember(userApplication.getClub());
            userApplication.approved();
        }
    }
}
