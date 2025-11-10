package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.UpdateMyInfoRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                updateUserMajor(request, user),
                updateUserLink(request, user),
                profileImage
        );
    }

    private List<UserMajor> updateUserMajor(UpdateMyInfoRequest request, User user) {
        return Optional.ofNullable(request.majors())
                .orElseGet(List::of)
                .stream()
                .map(major -> UserMajor.builder()
                        .user(user)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<UserLink> updateUserLink(UpdateMyInfoRequest request, User user) {
        return Optional.ofNullable(request.links())
                .orElseGet(List::of)
                .stream()
                .map(link -> UserLink.builder()
                        .user(user)
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
