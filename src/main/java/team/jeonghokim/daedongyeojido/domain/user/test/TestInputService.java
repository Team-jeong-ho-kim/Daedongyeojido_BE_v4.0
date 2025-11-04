package team.jeonghokim.daedongyeojido.domain.user.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUser;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserLink;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserMajor;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.repository.TestUserDetailRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.dto.request.TestMyInfoRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestInputService {
    private final UserFacade userFacade;
    private final S3Service s3Service;
    private final TestUserDetailRepository testUserDetailRepository;

    @Transactional
    public void execute(TestMyInfoRequest request) {
        TestUser testUser = userFacade.getCurrentTestUser();

        String profileImage = s3Service.upload(request.profileImage());

        testUser.inputMyInfo(
                request.phoneNumber(),
                request.introduction(),
                profileImage
        );

        List<TestUserMajor> majors = createUserMajor(request, testUser);
        List<TestUserLink> links = createUserLink(request, testUser);

        testUserDetailRepository.saveAll(testUser, majors, links);
    }

    private List<TestUserMajor> createUserMajor(TestMyInfoRequest request, TestUser user) {
        return Optional.ofNullable(request.majors())
                .orElseGet(List::of)
                .stream()
                .map(major -> TestUserMajor.builder()
                        .user(user)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<TestUserLink> createUserLink(TestMyInfoRequest request, TestUser user) {
        return Optional.ofNullable(request.links())
                .orElseGet(List::of)
                .stream()
                .map(link -> TestUserLink.builder()
                        .user(user)
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
