package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.UpdateMyInfoRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class UpdateMyInfoService {
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(UpdateMyInfoRequest request) {
        User user = userFacade.getCurrentUser();

        String profileImage = s3Service.update(user.getProfileImage(), request.profileImage());

        user.update(
                request.introduction(),
                request.majors(),
                request.links(),
                profileImage
        );
    }
}
