package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountId(String accountId);

    Optional<User> findByUserNameAndClassNumber(String userName, String classNumber);

    List<User> findAllByClub(Club club);
}
