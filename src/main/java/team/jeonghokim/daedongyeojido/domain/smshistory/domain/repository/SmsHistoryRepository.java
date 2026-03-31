package team.jeonghokim.daedongyeojido.domain.smshistory.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.SmsHistory;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsHistoryStatus;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsReferenceType;

import java.util.Collection;
import java.util.List;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    List<SmsHistory> findAllByReferenceTypeAndStatusInAndScheduledAtIsNotNull(
            SmsReferenceType referenceType,
            Collection<SmsHistoryStatus> statuses
    );
}
