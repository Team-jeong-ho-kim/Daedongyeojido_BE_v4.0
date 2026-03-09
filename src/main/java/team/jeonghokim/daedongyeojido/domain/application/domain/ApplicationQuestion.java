package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_application_question")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationQuestion extends BaseIdEntity {

    @Column(nullable = false, length = 150)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private ApplicationForm applicationForm;

    protected void setApplicationForm(ApplicationForm applicationForm) {
        if (this.applicationForm != null) {
            return;
        }
        this.applicationForm = applicationForm;
    }
}
