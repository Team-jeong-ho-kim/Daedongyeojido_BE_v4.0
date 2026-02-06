package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.UserApplication;

import java.util.Optional;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    Optional<UserApplication> findByUser_Id(Long userId);

    Optional<UserApplication> findByUser_IdAndClub_Id(Long userId, Long clubId);
}
