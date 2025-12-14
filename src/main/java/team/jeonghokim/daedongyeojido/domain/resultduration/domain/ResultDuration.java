package team.jeonghokim.daedongyeojido.domain.resultduration.domain;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultDuration extends BaseIdEntity {

    private LocalDateTime resultDuration;

    private boolean isExecuted;

    public ResultDuration(LocalDateTime resultDuration) {
        this.resultDuration = resultDuration;
        this.isExecuted = false;
    }

    public void execute() {
        this.isExecuted = true;
    }
}
