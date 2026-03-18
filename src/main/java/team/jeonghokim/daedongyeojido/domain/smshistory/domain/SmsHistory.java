package team.jeonghokim.daedongyeojido.domain.smshistory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsHistoryStatus;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.enums.SmsReferenceType;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_sms_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmsHistory extends BaseTimeIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SmsReferenceType referenceType;

    @Column(nullable = false)
    private Long referenceId;

    @Column(nullable = false, length = 30)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String messageType;

    @Column(nullable = false, length = 30)
    private String clubName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SmsHistoryStatus status;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    @Column(length = 1000)
    private String failureReason;

    @Builder
    public SmsHistory(
            SmsReferenceType referenceType,
            Long referenceId,
            String phoneNumber,
            String messageType,
            String clubName,
            SmsHistoryStatus status,
            LocalDateTime scheduledAt
    ) {
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.phoneNumber = phoneNumber;
        this.messageType = messageType;
        this.clubName = clubName;
        this.status = status;
        this.scheduledAt = scheduledAt;
    }

    public void markRequested() {
        this.status = SmsHistoryStatus.REQUESTED;
        this.failureReason = null;
    }

    public void markSent() {
        this.status = SmsHistoryStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.failureReason = null;
    }

    public void markFailed(String failureReason) {
        this.status = SmsHistoryStatus.FAILED;
        this.failureReason = failureReason;
    }
}
