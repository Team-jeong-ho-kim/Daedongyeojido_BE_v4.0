package team.jeonghokim.daedongyeojido.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
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

    @Column(unique = true, length = 11)
    private String phoneNumber;

    @Column(length = 30)
    private String introduction;

    @Column(nullable = false, length = 4)
    private String classNumber;

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

    public void coverInfo(String userName, String classNumber) {
        this.userName = userName;
        this.classNumber = classNumber;
    }

    public void update(String introduction, List<Major> majors, List<String> links, String profileImage) {
        this.introduction = introduction;
        updateMajors(majors);
        updateLinks(links);
        this.profileImage = profileImage;
    }

    private void updateMajors(List<Major> majors) {
        this.majors.clear();
        majors.stream()
                .map(major -> UserMajor.builder()
                        .major(major)
                        .build())
                .forEach(this::addUserMajor);
    }

    private void updateLinks(List<String> links) {
        this.links.clear();
        links.stream()
                .map(link -> UserLink.builder()
                        .link(link)
                        .build())
                .forEach(this::addUserLink);
    }

    private void addUserMajor(UserMajor userMajor) {
        userMajor.setUser(this);
        this.majors.add(userMajor);
    }

    private void addUserLink(UserLink userLink) {
        userLink.setUser(this);
        this.links.add(userLink);
    }

    public void approvedClub(Club club) {
        this.role = Role.CLUB_LEADER;
        this.club = club;
    }

    public void leaveClub() {
        this.club = null;
        this.role = Role.STUDENT;
    }

    public void approvedTeamMember(Club club) {
        this.role = Role.CLUB_MEMBER;
        this.club = club;
    }

    public void selectedClub(Club club) {
        this.club = club;
        this.role = Role.CLUB_MEMBER;
    }
}
