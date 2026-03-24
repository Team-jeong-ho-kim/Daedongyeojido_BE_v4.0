package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.ClubAlarm;
import team.jeonghokim.daedongyeojido.domain.club.domain.enums.ClubStatus;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(
    name = "tbl_club",
    uniqueConstraints = {
            @UniqueConstraint(
                name = "unique_idx_club",
                columnNames = {"club_name", "account_id"}
            )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseIdEntity {

    @Column(length = 20, nullable = false)
    private String clubName;

    @Column(length = 200, nullable = false)
    private String clubImage;

    @Column(nullable = false)
    private String clubCreationForm;

    @Column(length = 30, nullable = false)
    private String oneLiner;

    @Column(length = 500, nullable = false)
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubStatus clubStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User clubApplicant;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMajor> clubMajors = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubLink> clubLinks = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubAlarm> alarms = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Builder
    public Club(
            String clubName,
            String clubImage,
            String clubCreationForm,
            String oneLiner,
            String introduction,
            ClubStatus clubStatus,
            User clubApplicant,
            List<ClubMajor> clubMajors,
            List<ClubLink> clubLinks,
            Teacher teacher
    ) {
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.clubCreationForm = clubCreationForm;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.clubStatus = clubStatus;
        this.clubApplicant = clubApplicant;
        addClubMajors(clubMajors);
        addClubLinks(clubLinks);
        this.teacher = teacher;
    }

    private void addClubMajors(List<ClubMajor> clubMajors) {
        clubMajors.forEach(major ->{
            major.setClub(this);
            this.clubMajors.add(major);
        });
    }

    private void addClubLinks(List<ClubLink> clubLinks) {
        clubLinks.forEach(link ->{
            link.setClub(this);
            this.clubLinks.add(link);
        });
    }

    public void clubOpen() {
        this.clubStatus = ClubStatus.OPEN;
    }

    public void clubRejected() {
        this.clubStatus = ClubStatus.REJECTED;
    }

    public void updateClub(
            String clubName,
            String clubImage,
            String oneLiner,
            String introduction,
            List<Major> majors,
            List<String> links
    ) {
        this.clubName = clubName != null ? clubName : this.clubName;
        this.clubImage = clubImage != null ? clubImage : this.clubImage;
        this.oneLiner = oneLiner != null ? oneLiner : this.oneLiner;
        this.introduction = introduction != null ? introduction : this.introduction;

        if (majors != null) {
            updateMajors(majors);
        }

        if (links != null) {
            updateLinks(links);
        }
    }

    private void updateMajors(List<Major> majors) {
        this.clubMajors.clear();
        majors.stream()
                .filter(Objects::nonNull)
                .map(major -> ClubMajor.builder()
                        .major(major)
                        .build())
                .forEach(this::addClubMajor);
    }

    private void updateLinks(List<String> links) {
        this.clubLinks.clear();
        links.stream()
                .filter(Objects::nonNull)
                .filter(link -> !link.isBlank())
                .map(link -> ClubLink.builder()
                        .link(link)
                        .build())
                .forEach(this::addClubLink);
    }
    
    private void addClubMajor(ClubMajor clubMajor) {
        clubMajor.setClub(this);
        this.clubMajors.add(clubMajor);
    }

    private void addClubLink(ClubLink clubLink) {
        clubLink.setClub(this);
        this.clubLinks.add(clubLink);
    }
}
