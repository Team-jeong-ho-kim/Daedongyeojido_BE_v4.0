package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubApplication;

import java.util.Optional;

public interface ClubApplicationRepository extends JpaRepository<ClubApplication, Long> {

    Optional<ClubApplication> findByClubId(Long clubId);
}
