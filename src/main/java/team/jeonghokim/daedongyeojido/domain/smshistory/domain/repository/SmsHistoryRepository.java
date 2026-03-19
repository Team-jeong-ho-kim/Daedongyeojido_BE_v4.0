package team.jeonghokim.daedongyeojido.domain.smshistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.SmsHistory;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {
}
