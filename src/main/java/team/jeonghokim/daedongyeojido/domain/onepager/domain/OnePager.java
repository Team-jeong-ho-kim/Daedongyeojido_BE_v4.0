package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_onepager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnePager extends BaseIdEntity {
    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false,  length = 500)
    private String description;

    @OneToOne
    @JoinColumn(name = "file_id", unique = true)
    private File formFile;

    private String formUrl;

    @Column(nullable = false, length = 4)
    private String teacherName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OnePagerDurationType onePagerDurationType;

    @Column(nullable = true)
    private LocalDateTime onePagerDuration;

    private

    @Builder
    public OnePager(
            String title,
            String description,
            File formFile,
            String formUrl,
            String teacherName,
            OnePagerDurationType onePagerDurationType,
            LocalDateTime onePagerDuration
    ) {
        this.title = title;
        this.description = description;
        this.formFile = formFile;
        this.formUrl = formUrl;
        this.teacherName = teacherName;
        this.onePagerDurationType = onePagerDurationType;
        this.onePagerDuration = onePagerDuration;
    }

    public void update(
            String title,
            String description,
            File formFile,
            String formUrl,
            String teacherName,
            OnePagerDurationType onePagerDurationType,
            LocalDateTime onePagerDuration
    ){
        this.title = title;
        this.description = description;
        this.formFile = formFile;
        this.formUrl = formUrl;
        this.teacherName = teacherName;
        this.onePagerDurationType = onePagerDurationType;
        this.onePagerDuration = onePagerDuration;
    }
}
