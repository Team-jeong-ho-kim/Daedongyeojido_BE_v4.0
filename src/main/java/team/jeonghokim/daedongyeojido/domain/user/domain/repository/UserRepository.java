package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountId(String accountId);

    Optional<User> findByUserNameAndClassNumber(String userName, Integer classNumber);
}
