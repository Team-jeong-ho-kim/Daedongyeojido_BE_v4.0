package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {

    boolean existsByClubName(String clubName);
}
