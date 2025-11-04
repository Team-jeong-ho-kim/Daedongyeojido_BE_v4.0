package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;

import java.util.List;

public interface ClubLinkRepository extends JpaRepository<ClubLink, Long> {

    List<ClubLink> findAllByClub(Club club);

    void deleteAllByClub(Club club);
}
