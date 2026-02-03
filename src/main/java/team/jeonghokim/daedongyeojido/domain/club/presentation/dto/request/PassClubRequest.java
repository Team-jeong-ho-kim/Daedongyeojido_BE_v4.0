package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record PassClubRequest(
        @NotNull(message = "isPassed 값은 필수입니다.")
        Boolean isPassed
) {
}
