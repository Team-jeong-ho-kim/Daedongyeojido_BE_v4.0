package team.jeonghokim.daedongyeojido.domain.application.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_application_major")
@Getter
@NoArgsConstructor
public class ApplicationMajor extends BaseIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_form_id", nullable = false)
    private ApplicationForm applicationForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Major major;

    @Builder
    public ApplicationMajor(Major major) {
        this.major = major;
    }

    protected void setApplicationForm(ApplicationForm applicationForm) {
        if (this.applicationForm != null) {
            return;
        }
        this.applicationForm = applicationForm;
    }
}
