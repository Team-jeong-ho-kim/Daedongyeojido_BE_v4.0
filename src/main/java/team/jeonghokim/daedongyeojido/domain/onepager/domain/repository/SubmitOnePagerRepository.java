package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;

public interface SubmitOnePagerRepository extends JpaRepository<SubmitOnePager, Long> {
    List<SubmitOnePager> findByFormOnePager(OnePager formOnePager);
}
