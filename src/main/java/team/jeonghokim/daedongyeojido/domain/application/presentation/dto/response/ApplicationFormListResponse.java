package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import team.jeonghokim.daedongyeojido.domain.application.domain.ApplicationForm;

import java.time.LocalDate;

public record ApplicationFormListResponse(
        String clubName,
        String clubImage,
        LocalDate submissionDuration
) {
}
