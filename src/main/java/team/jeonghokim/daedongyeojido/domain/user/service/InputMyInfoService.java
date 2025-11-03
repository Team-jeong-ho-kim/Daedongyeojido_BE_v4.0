package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;

@Service
@RequiredArgsConstructor
public class InputMyInfoService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Transactional
    public void execute(MyInfoRequest request) {
        User user = userFacade.getCurrentUser();

        user.inputMyInfo(request.phoneNumber(), request.introduction(), request.majors(), request.links());
    }
}
