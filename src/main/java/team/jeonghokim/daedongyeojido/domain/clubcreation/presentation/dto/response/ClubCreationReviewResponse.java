package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationReview;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationReviewDecision;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ReviewerType;

import java.time.LocalDateTime;

@Builder
public record ClubCreationReviewResponse(
        Long reviewId,
        ReviewerType reviewerType,
        String reviewerName,
        int revision,
        ClubCreationReviewDecision decision,
        String feedback,
        LocalDateTime updatedAt
) {
    public static ClubCreationReviewResponse from(ClubCreationReview review) {
        return ClubCreationReviewResponse.builder()
                .reviewId(review.getId())
                .reviewerType(review.getReviewerType())
                .reviewerName(review.getReviewerName())
                .revision(review.getRevision())
                .decision(review.getDecision())
                .feedback(review.getFeedback())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
