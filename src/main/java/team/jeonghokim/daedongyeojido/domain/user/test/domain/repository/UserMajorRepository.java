package team.jeonghokim.daedongyeojido.domain.user.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.test.domain.TestUserMajor;

public interface UserMajorRepository extends JpaRepository<TestUserMajor, Long> {
}
