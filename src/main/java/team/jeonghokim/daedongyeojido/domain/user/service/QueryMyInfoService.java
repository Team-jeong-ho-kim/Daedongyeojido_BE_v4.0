package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;

@Service
@RequiredArgsConstructor
public class QueryMyInfoService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyInfoResponse execute() {
        User user = userFacade.getCurrentUser();

        return MyInfoResponse.of(user);
    }
}
