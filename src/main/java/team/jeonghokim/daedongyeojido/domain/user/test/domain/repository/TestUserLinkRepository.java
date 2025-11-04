package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUser;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserLink;

import java.util.List;

public interface TestUserLinkRepository extends JpaRepository<TestUserLink, Long> {

    List<TestUserLink> findAllByUser(TestUser user);
}
