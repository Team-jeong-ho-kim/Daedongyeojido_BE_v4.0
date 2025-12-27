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

    public ResultDuration(LocalDateTime resultDuration) {
        this.resultDuration = resultDuration;
    }

    public void update(LocalDateTime resultDuration) {
        this.resultDuration = resultDuration;
    }
}
