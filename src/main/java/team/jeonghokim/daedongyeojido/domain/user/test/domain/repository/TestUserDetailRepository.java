package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUser;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserLink;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserMajor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestUserDetailRepository {
    private final UserMajorRepository userMajorRepository;
    private final UserLinkRepository userLinkRepository;

    @Transactional
    public void saveAll(TestUser user, List<TestUserMajor> majors, List<TestUserLink> links) {
        userMajorRepository.saveAll(majors);
        userLinkRepository.saveAll(links);
    }

    @Transactional(readOnly = true)
    public List<TestUserMajor> findMajorsByUser(TestUser user) {
        return userMajorRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<TestUserLink> findLinksByUser(TestUser user) {
        return userLinkRepository.findAllByUser(user);
    }
}
