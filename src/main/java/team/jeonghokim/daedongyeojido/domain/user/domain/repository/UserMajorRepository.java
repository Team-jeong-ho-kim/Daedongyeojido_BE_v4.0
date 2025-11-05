package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserMajor;

import java.util.List;

public interface UserMajorRepository extends JpaRepository<UserMajor, Long> {

    List<UserMajor> findAllByUserId(Long userId);
}
