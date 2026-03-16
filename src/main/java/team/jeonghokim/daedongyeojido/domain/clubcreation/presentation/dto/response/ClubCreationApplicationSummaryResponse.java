package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ClubCreationApplicationSummaryResponse(
        Long applicationId,
        String clubName,
        String clubImage,
        String introduction,
        ClubCreationApplicationStatus status,
        int revision,
        List<Major> majors,
        String applicantName,
        LocalDateTime lastSubmittedAt
) {
    public static ClubCreationApplicationSummaryResponse from(ClubCreationApplication application) {
        return ClubCreationApplicationSummaryResponse.builder()
                .applicationId(application.getId())
                .clubName(application.getClubName())
                .clubImage(application.getClubImage())
                .introduction(application.getIntroduction())
                .status(application.getStatus())
                .revision(application.getRevision())
                .majors(application.getMajors())
                .applicantName(application.getApplicant().getUserName())
                .lastSubmittedAt(application.getLastSubmittedAt())
                .build();
    }
}
