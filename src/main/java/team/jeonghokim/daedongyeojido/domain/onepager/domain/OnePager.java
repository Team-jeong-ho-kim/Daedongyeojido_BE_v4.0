package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerState;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
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

    @JoinColumn(name = "one_pager_teacher_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OnePagerDurationType onePagerDurationType;

    @Column(nullable = true)
    private LocalDateTime onePagerDuration;

    @Builder
    public OnePager(
            String title,
            String description,
            File formFile,
            String formUrl,
            Teacher teacher,
            OnePagerDurationType onePagerDurationType,
            LocalDateTime onePagerDuration,
            OnePagerState state
    ) {
        this.title = title;
        this.description = description;
        this.formFile = formFile;
        this.formUrl = formUrl;
        this.teacher = teacher;
        this.onePagerDurationType = onePagerDurationType;
        this.onePagerDuration = onePagerDuration;
    }

    public void update(
            String title,
            String description,
            File formFile,
            String formUrl,
            Teacher teacher,
            OnePagerDurationType onePagerDurationType,
            LocalDateTime onePagerDuration
    ){
        this.title = title;
        this.description = description;
        this.formFile = formFile;
        this.formUrl = formUrl;
        this.teacher = teacher;
        this.onePagerDurationType = onePagerDurationType;
        this.onePagerDuration = onePagerDuration;
    }

    public String getFormFileUrl() {
        return (formFile == null) ? null : formFile.getFileUrl();
    }

    public String getFormFileName() {
        return (formFile == null) ? null : formFile.getFileName();
    }
}
