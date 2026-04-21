package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.file.domain.File;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDuration;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_onepager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OnePager extends BaseIdEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "file_id", unique = true)
    private File formFile;

    private String formUrl;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private OnePagerDuration onePagerDuration;
}
