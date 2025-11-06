package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryMyInfoService {
    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyInfoResponse execute() {
        User user = userFacade.getCurrentUser();

        return MyInfoResponse.builder()
                .userName(user.getUserName())
                .classNumber(user.getClassNumber())
                .introduction(user.getIntroduction())
                .club(Optional.ofNullable(user.getClub()).map(club -> club.getClubName()).orElse(null))
                .major(user.getMajors().stream().map(UserMajor::getMajor).toList())
                .link(user.getLinks().stream().map(UserLink::getLink).toList())
                .profileImage(user.getProfileImage())
                .build();
    }
}
