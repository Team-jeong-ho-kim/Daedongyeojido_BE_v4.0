package team.jeonghokim.daedongyeojido.domain.announcement.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.global.entity.BaseIdEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_announcement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseIdEntity {

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false, length = 200)
    private String introduction;

    @Column(nullable = false, length = 100)
    private String talentDescription;

    @Column(length = 150)
    private String assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementMajor> announcementMajors = new ArrayList<>();

    @Builder
    public Announcement(
            String title,
            LocalDate deadline,
            String introduction,
            String talentDescription,
            String assignment,
            Club club,
            List<AnnouncementMajor> announcementMajors
    ) {
        this.title = title;
        this.deadline = deadline;
        this.introduction = introduction;
        this.talentDescription = talentDescription;
        this.assignment = assignment;
        this.club = club;
        addAnnouncementMajors(announcementMajors);
    }

    private void addAnnouncementMajors(List<AnnouncementMajor> announcementMajors) {
        announcementMajors.forEach(major ->{
            major.setAnnouncement(this);
            this.announcementMajors.add(major);
        });
    }
}
