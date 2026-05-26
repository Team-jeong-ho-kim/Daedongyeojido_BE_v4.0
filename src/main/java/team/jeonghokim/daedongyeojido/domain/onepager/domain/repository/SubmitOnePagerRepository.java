package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;
import java.util.Optional;

public interface SubmitOnePagerRepository extends JpaRepository<SubmitOnePager, Long>, SubmitOnePagerRepositoryCustom {
    List<SubmitOnePager> findByFormOnePager(OnePager formOnePager);

    Optional<SubmitOnePager> findByClubAndFormOnePager(Club club, OnePager formOnePager);
}
