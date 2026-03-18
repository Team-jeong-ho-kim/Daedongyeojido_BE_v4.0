package team.jeonghokim.daedongyeojido.domain.smshistory.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.SmsHistory;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsHistoryStatus;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsReferenceType;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.repository.SmsHistoryRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SmsHistoryService {

    private static final int MAX_FAILURE_REASON_LENGTH = 1000;

    private final SmsHistoryRepository smsHistoryRepository;

    @Transactional
    public Long createImmediate(
            SmsReferenceType referenceType,
            Long referenceId,
            User receiver,
            Message message,
            String clubName
    ) {
        return smsHistoryRepository.save(
                SmsHistory.builder()
                        .referenceType(referenceType)
                        .referenceId(referenceId)
                        .phoneNumber(receiver.getPhoneNumber())
                        .userName(receiver.getUserName())
                        .classNumber(receiver.getClassNumber())
                        .messageType(message.name())
                        .clubName(clubName)
                        .status(SmsHistoryStatus.REQUESTED)
                        .build()
        ).getId();
    }

    @Transactional
    public Long createQueued(
            SmsReferenceType referenceType,
            Long referenceId,
            User receiver,
            Message message,
            String clubName,
            LocalDateTime scheduledAt
    ) {
        return smsHistoryRepository.save(
                SmsHistory.builder()
                        .referenceType(referenceType)
                        .referenceId(referenceId)
                        .phoneNumber(receiver.getPhoneNumber())
                        .userName(receiver.getUserName())
                        .classNumber(receiver.getClassNumber())
                        .messageType(message.name())
                        .clubName(clubName)
                        .status(SmsHistoryStatus.QUEUED)
                        .scheduledAt(scheduledAt)
                        .build()
        ).getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markRequested(Long smsHistoryId) {
        getById(smsHistoryId).markRequested();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markSent(Long smsHistoryId) {
        getById(smsHistoryId).markSent();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(Long smsHistoryId, Throwable throwable) {
        getById(smsHistoryId).markFailed(summarize(throwable));
    }

    private SmsHistory getById(Long smsHistoryId) {
        return smsHistoryRepository.findById(smsHistoryId)
                .orElseThrow(() -> new EntityNotFoundException("SMS history not found: " + smsHistoryId));
    }

    private String summarize(Throwable throwable) {
        String message = throwable.getClass().getSimpleName();
        if (throwable.getMessage() != null && !throwable.getMessage().isBlank()) {
            message = message + ": " + throwable.getMessage();
        }

        return message.length() > MAX_FAILURE_REASON_LENGTH
                ? message.substring(0, MAX_FAILURE_REASON_LENGTH)
                : message;
    }
}
