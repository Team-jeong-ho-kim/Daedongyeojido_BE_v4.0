package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {

    boolean existsByClubName(String clubName);

    boolean existsByClubApplicant(User clubApplicant);
}
