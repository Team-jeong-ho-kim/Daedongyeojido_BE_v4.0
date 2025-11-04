package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.mapper.UserMapper;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class InputMyInfoService {
    private final UserFacade userFacade;
    private final UserMapper userMapper;
    private final S3Service s3Service;

    @Transactional
    public void execute(MyInfoRequest request) {
        User user = userFacade.getCurrentUser();

        String profileImage = s3Service.upload(request.profileImage());

        user.inputMyInfo(
                request.phoneNumber(),
                request.introduction(),
                userMapper.toUserMajors(request, user),
                userMapper.toUserLinks(request, user),
                profileImage
        );
    }
}
