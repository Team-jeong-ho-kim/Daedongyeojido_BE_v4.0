package team.jeonghokim.daedongyeojido.domain.teacher.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_teacher")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Teacher extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false, length = 20)
    private String teacherName;

    @Column(nullable = false)
    private String password;
}
