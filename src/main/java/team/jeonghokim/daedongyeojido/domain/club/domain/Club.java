package team.jeonghokim.daedongyeojido.domain.club.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.Alarm;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 30, nullable = false)
    private String oneLiner;

    @Column(length = 500, nullable = false)
    private String introduction;

    @Column(nullable = false)
    private Boolean isOpen;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User clubApplicant;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMajor> clubMajors = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubLink> clubLinks = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubAlarm> alarms = new ArrayList<>();

    @Builder
    public Club(
            String clubName,
            String clubImage,
            String oneLiner,
            String introduction,
            Boolean isOpen,
            User clubApplicant,
            List<ClubMajor> clubMajors,
            List<ClubLink> clubLinks
    ) {
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.isOpen = isOpen;
        this.clubApplicant = clubApplicant;
        addClubMajors(clubMajors);
        addClubLinks(clubLinks);
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
        this.isOpen = true;
    }

    public void updateClub(ClubRequest request, String clubImage) {
        this.clubName = request.getClubName();
        this.clubImage = clubImage;
        this.oneLiner = request.getOneLiner();
        this.introduction = request.getIntroduction();

        updateMajors(request.getMajor());
        updateLinks(request.getLink());
    }

    private void updateMajors(List<Major> majors) {
        this.clubMajors.clear();
        majors.stream()
                .map(major -> ClubMajor.builder()
                        .major(major)
                        .build())
                .forEach(this::addClubMajor);
    }

    private void updateLinks(List<String> links) {
        this.clubLinks.clear();
        links.stream()
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
