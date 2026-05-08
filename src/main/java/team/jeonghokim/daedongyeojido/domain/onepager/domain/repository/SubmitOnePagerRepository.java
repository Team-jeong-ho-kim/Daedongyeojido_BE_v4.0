package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;

public interface SubmitOnePagerRepository extends JpaRepository<SubmitOnePager, Long> {
    @Query("SELECT s FROM SubmitOnePager s JOIN FETCH s.formOnePager WHERE s.club = :club")
    List<SubmitOnePager> findByClub(@Param("club") Club club);
}
