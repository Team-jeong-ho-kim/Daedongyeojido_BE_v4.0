package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false, length = 4)
    private String userName;

    @Column(nullable = false, length = 4)
    private String classNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(length = 30)
    private String introduction;

    @Column(length = 100)
    private List<String> links;

    @Column(length = 300)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private List<Major> majors;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private Role role;
}
