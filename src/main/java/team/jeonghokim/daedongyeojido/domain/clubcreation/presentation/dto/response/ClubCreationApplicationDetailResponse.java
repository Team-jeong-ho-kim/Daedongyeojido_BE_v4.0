package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ClubCreationApplicationDetailResponse(
        Long applicationId,
        ClubCreationApplicationStatus status,
        int revision,
        String clubName,
        String clubImage,
        String clubCreationForm,
        String oneLiner,
        String introduction,
        List<Major> majors,
        List<String> links,
        ClubCreationApplicantResponse applicant,
        LocalDateTime submittedAt,
        LocalDateTime lastSubmittedAt,
        List<ClubCreationReviewResponse> currentReviews,
        List<ClubCreationReviewResponse> reviewHistory
) {
    public static ClubCreationApplicationDetailResponse of(
            ClubCreationApplication application,
            ClubCreationApplicantResponse applicant,
            List<ClubCreationReviewResponse> currentReviews,
            List<ClubCreationReviewResponse> reviewHistory
    ) {
        return ClubCreationApplicationDetailResponse.builder()
                .applicationId(application.getId())
                .status(application.getStatus())
                .revision(application.getRevision())
                .clubName(application.getClubName())
                .clubImage(application.getClubImage())
                .clubCreationForm(application.getClubCreationForm())
                .oneLiner(application.getOneLiner())
                .introduction(application.getIntroduction())
                .majors(application.getMajors())
                .links(application.getLinks())
                .applicant(applicant)
                .submittedAt(application.getSubmittedAt())
                .lastSubmittedAt(application.getLastSubmittedAt())
                .currentReviews(currentReviews)
                .reviewHistory(reviewHistory)
                .build();
    }
}
