package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserLink;

public interface UserLinkRepository extends JpaRepository<TestUserLink, Long> {
}
