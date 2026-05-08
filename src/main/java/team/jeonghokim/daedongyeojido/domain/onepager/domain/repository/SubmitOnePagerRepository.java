package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

public interface SubmitOnePagerRepository extends JpaRepository<SubmitOnePager, Long>, SubmitOnePagerRepositoryCustom {
}
