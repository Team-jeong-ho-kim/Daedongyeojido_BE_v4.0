package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.auth.presentation.dto.request.LoginRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserLinkRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserMajorRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;
import team.jeonghokim.daedongyeojido.global.xquare.XquareClient;
import team.jeonghokim.daedongyeojido.global.xquare.dto.XquareResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMyInfoService {
    private final UserFacade userFacade;
    private final XquareClient xquareClient;
    private final UserRepository userRepository;
    private final UserMajorRepository userMajorRepository;
    private final UserLinkRepository userLinkRepository;

    @Transactional(readOnly = true)
    public MyInfoResponse execute(Long userId) {
        User user = userFacade.getCurrentUser();

        LoginRequest request = LoginRequest.builder()
                .accountId(user.getAccountId())
                .password(user.getPassword())
                .build();

        XquareResponse xquareUser = xquareClient.getUser(request);

        List<Major> majors = userMajorRepository.findAllByUserId(userId)
                .stream()
                .map(UserMajor::getMajor)
                .toList();

        List<String> links = userLinkRepository.findAllByUserId(userId)
                .stream()
                .map(UserLink::getLink)
                .toList();

        return MyInfoResponse.builder()
                .userName(user.getUserName())
                .classNumber(xquareUser.classNum())
                .introduction(user.getIntroduction())
                .club(user.getClub().getClubName())
                .major(majors)
                .link(links)
                .profileImage(user.getProfileImage())
                .build();
    }
}
