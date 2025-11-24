package team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response;

import java.time.LocalDate;

public record SubmissionResponse(
        Long submissionId,
        String clubName,
        String clubImage,
        String applicationStatus,
        LocalDate submissionDuration
) {
}
