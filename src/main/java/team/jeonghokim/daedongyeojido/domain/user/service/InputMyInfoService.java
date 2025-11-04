package team.jeonghokim.daedongyeojido.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserLinkRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserMajorRepository;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InputMyInfoService {
    private final UserFacade userFacade;
    private final S3Service s3Service;
    private final UserMajorRepository userMajorRepository;
    private final UserLinkRepository userLinkRepository;

    @Transactional
    public void execute(MyInfoRequest request) {
        User user = userFacade.getCurrentUser();

        String profileImage = s3Service.upload(request.profileImage());

        user.inputMyInfo(
                request.phoneNumber(),
                request.introduction(),
                profileImage
        );

        List<UserMajor> majors = createUserMajor(request, user);
        List<UserLink> links = createUserLink(request, user);

        userMajorRepository.saveAll(majors);
        userLinkRepository.saveAll(links);
    }

    private List<UserMajor> createUserMajor(MyInfoRequest request, User user) {
        return Optional.ofNullable(request.majors())
                .orElseGet(List::of)
                .stream()
                .map(major -> UserMajor.builder()
                        .user(user)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<UserLink> createUserLink(MyInfoRequest request, User user) {
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
