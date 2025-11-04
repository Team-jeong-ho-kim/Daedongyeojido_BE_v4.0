package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUser;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
    Optional<TestUser> findByAccountId(String accountId);
}
