package team.jeonghokim.daedongyeojido.domain.resultduration.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_result_duration")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultDuration extends BaseIdEntity {

    private LocalDateTime resultDurationTime;

    @Enumerated(EnumType.STRING)
    private Status smsStatus;

    @Enumerated(EnumType.STRING)
    private Status alarmStatus;

    public ResultDuration(LocalDateTime resultDuration) {
        this.resultDurationTime = resultDuration;
        this.smsStatus = Status.PENDING;
        this.alarmStatus = Status.PENDING;
    }

    public void update(LocalDateTime resultDuration) {
        this.resultDurationTime = resultDuration;
    }

    public void smsRequested() {
        this.smsStatus = Status.REQUESTED;
    }

    public void alarmRequested() {
        this.alarmStatus = Status.REQUESTED;
    }
}
