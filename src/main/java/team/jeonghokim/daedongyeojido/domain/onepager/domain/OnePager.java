package team.jeonghokim.daedongyeojido.domain.onepager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Table(name = "tbl_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OnePager extends BaseIdEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false, unique = true)
    private String fileUrl;

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private String onePagerDuration;
}
