package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;

import java.time.LocalDate;

public record ApplicationFormListResponse(
        String applicationFormTitle,
        String clubName,
        String clubImage,
        LocalDate submissionDuration
) {
    public static ApplicationFormListResponse of(ApplicationForm applicationForm) {
        return new ApplicationFormListResponse(
                applicationForm.getApplicationFormTitle(),
                applicationForm.getClub().getClubName(),
                applicationForm.getClub().getClubImage(),
                applicationForm.getSubmissionDuration()
        );
    }
}
