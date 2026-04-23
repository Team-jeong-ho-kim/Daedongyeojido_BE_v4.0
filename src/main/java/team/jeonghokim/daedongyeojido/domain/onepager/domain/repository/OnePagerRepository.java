package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;

import java.time.LocalDateTime;
import java.util.List;

public interface OnePagerRepository extends JpaRepository<OnePager, Long> {
    List<OnePager> findByOnePagerDurationTypeOrOnePagerDurationAfter(OnePagerDurationType onePagerDurationType, LocalDateTime onePagerDuration);
}
