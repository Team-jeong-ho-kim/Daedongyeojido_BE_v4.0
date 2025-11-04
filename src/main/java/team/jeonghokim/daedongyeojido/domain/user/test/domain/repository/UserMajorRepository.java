package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUser;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserMajor;

import java.util.List;

public interface UserMajorRepository extends JpaRepository<TestUserMajor, Long> {
    List<TestUserMajor> findAllByUser(TestUser user);
}
