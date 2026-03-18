package team.jeonghokim.daedongyeojido.domain.clubcreation.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tbl_club_creation_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCreationApplication extends BaseTimeIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

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
    private ClubCreationApplicationStatus status;

    @Column(nullable = false)
    private int revision;

    private LocalDateTime submittedAt;

    private LocalDateTime lastSubmittedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_club_creation_application_major", joinColumns = @JoinColumn(name = "application_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false)
    private List<Major> majors = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_club_creation_application_link", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "link_value", nullable = false)
    private List<String> links = new ArrayList<>();

    @OneToMany(mappedBy = "application")
    private List<ClubCreationReview> reviews = new ArrayList<>();

    @Builder
    public ClubCreationApplication(
            User applicant,
            Teacher teacher,
            String clubName,
            String clubImage,
            String clubCreationForm,
            String oneLiner,
            String introduction,
            ClubCreationApplicationStatus status,
            int revision,
            List<Major> majors,
            List<String> links
    ) {
        this.applicant = applicant;
        this.teacher = teacher;
        this.clubName = clubName;
        this.clubImage = clubImage;
        this.clubCreationForm = clubCreationForm;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.status = status;
        this.revision = revision;
        this.majors = new ArrayList<>(majors);
        this.links = new ArrayList<>(links);
    }

    public void update(
            String clubName,
            String clubImage,
            String clubCreationForm,
            String oneLiner,
            String introduction,
            List<Major> majors,
            List<String> links
    ) {
        this.clubName = clubName != null ? clubName : this.clubName;
        this.clubImage = clubImage != null ? clubImage : this.clubImage;
        this.clubCreationForm = clubCreationForm != null ? clubCreationForm : this.clubCreationForm;
        this.oneLiner = oneLiner != null ? oneLiner : this.oneLiner;
        this.introduction = introduction != null ? introduction : this.introduction;

        if (majors != null) {
            this.majors.clear();
            this.majors.addAll(majors);
        }

        if (links != null) {
            this.links.clear();
            this.links.addAll(links);
        }
    }

    public void submit() {
        LocalDateTime now = LocalDateTime.now();
        if (this.submittedAt == null) {
            this.submittedAt = now;
        }
        this.lastSubmittedAt = now;

        if (this.status == ClubCreationApplicationStatus.CHANGES_REQUESTED) {
            this.revision += 1;
        } else if (this.revision == 0) {
            this.revision = 1;
        }

        this.status = ClubCreationApplicationStatus.SUBMITTED;
    }

    public void startReview() {
        this.status = ClubCreationApplicationStatus.UNDER_REVIEW;
    }

    public void requestChanges() {
        this.status = ClubCreationApplicationStatus.CHANGES_REQUESTED;
    }

    public void approve() {
        this.status = ClubCreationApplicationStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = ClubCreationApplicationStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
    }
}
