package team.jeonghokim.daedongyeojido.domain.clubcreation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationReviewDecision;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;
import team.jeonghokim.daedongyeojido.global.entity.BaseTimeIdEntity;

@Entity
@Getter
@Table(
        name = "tbl_club_creation_review",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_idx_club_creation_review",
                        columnNames = {"application_id", "reviewer_type", "reviewer_id", "revision"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCreationReview extends BaseTimeIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private ClubCreationApplication application;

    @Enumerated(EnumType.STRING)
    @Column(name = "reviewer_type", nullable = false, length = 20)
    private ReviewerType reviewerType;

    @Column(name = "reviewer_id", nullable = false)
    private Long reviewerId;

    @Column(name = "reviewer_name", nullable = false, length = 20)
    private String reviewerName;

    @Column(nullable = false)
    private int revision;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClubCreationReviewDecision decision;

    @Column(length = 1000)
    private String feedback;

    @Builder
    public ClubCreationReview(
            ClubCreationApplication application,
            ReviewerType reviewerType,
            Long reviewerId,
            String reviewerName,
            int revision,
            ClubCreationReviewDecision decision,
            String feedback
    ) {
        this.application = application;
        this.reviewerType = reviewerType;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.revision = revision;
        this.decision = decision;
        this.feedback = feedback;
    }

    public void update(ClubCreationReviewDecision decision, String feedback) {
        this.decision = decision;
        this.feedback = feedback;
    }
}
