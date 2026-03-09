package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserLink;

import java.util.List;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {

    List<UserLink> findAllByUserId(Long userId);
}
