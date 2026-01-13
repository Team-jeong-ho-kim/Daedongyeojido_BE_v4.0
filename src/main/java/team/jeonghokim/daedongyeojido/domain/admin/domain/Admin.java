package team.jeonghokim.daedongyeojido.domain.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity(name = "tbl_admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Role role;

    @Builder
    public Admin(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
        this.role = Role.ADMIN;
    }
}
