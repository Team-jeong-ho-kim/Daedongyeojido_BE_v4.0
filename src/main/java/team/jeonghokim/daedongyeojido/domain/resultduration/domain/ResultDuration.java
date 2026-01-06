package team.jeonghokim.daedongyeojido.domain.resultduration.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultDuration extends BaseIdEntity {

    private LocalDateTime resultDuration;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ResultDuration(LocalDateTime resultDuration) {
        this.resultDuration = resultDuration;
        this.status = Status.PENDING;
    }

    public void update(LocalDateTime resultDuration) {
        this.resultDuration = resultDuration;
    }
}
