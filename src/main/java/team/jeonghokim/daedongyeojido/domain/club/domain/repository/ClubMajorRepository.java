package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;

import java.util.List;

public interface ClubMajorRepository extends JpaRepository<ClubMajor, Long> {

    List<ClubMajor> findAllByClub(Club club);
}
