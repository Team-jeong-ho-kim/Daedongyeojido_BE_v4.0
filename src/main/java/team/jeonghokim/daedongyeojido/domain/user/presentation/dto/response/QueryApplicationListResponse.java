package team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response;

import java.time.LocalDate;

public record QueryApplicationListResponse(
        Long submissionId,
        String clubName,
        String clubImage,
        String applicationStatus,
        LocalDate submissionDuration
) {
}
