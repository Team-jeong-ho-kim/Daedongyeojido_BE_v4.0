package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_application_question")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationQuestion extends BaseIdEntity {

    @Column(nullable = false, length = 150)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private ApplicationForm applicationForm;
}
