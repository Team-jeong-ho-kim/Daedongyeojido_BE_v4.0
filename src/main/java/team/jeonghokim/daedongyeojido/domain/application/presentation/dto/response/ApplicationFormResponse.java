package team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response;

import java.util.List;

public record ApplicationFormResponse(
        List<String> content,
        String submissionDuration
) {
}
