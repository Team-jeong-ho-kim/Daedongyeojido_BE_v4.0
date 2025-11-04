package team.jeonghokim.daedongyeojido.domain.user.test.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUser extends BaseIdEntity {

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false, length = 4)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 30)
    private String introduction;

    @Column(length = 300)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public void inputMyInfo(String phoneNumber, String introduction, String profileImage) {
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }
}
