package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
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

    @Column(nullable = false)
    private String password;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 30)
    private String introduction;

    @Column(nullable = false, length = 4)
    private Integer classNumber;

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

    public void inputMyInfo(String phoneNumber, String introduction,
                            List<UserMajor> majors, List<UserLink> links, String profileImage) {
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        this.majors.clear();
        this.majors.addAll(majors);
        this.links.clear();
        this.links.addAll(links);
        this.profileImage = profileImage;
    }

    public void coverInfo(String userName, Integer classNumber) {
        this.userName = userName;
        this.classNumber = classNumber;
    }

    public void update(String introduction, List<UserMajor> majors, List<UserLink> links, String profileImage) {
        this.introduction = introduction;
        this.majors.clear();
        this.majors.addAll(majors);
        this.links.clear();
        this.links.addAll(links);
        this.profileImage = profileImage;
    }

    public void approvedClub(Club club) {
        this.role = Role.CLUB_MEMBER;
        this.club = club;
    }
}
