package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tbl_user")
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLink> links = new ArrayList<>();

    @Column(length = 300)
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMajor> majors =  new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
}
