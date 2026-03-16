package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationReviewDecision;

public record UpsertClubCreationReviewRequest(
        @NotNull(message = "검토 결과는 필수입니다.")
        ClubCreationReviewDecision decision,

        String feedback
) {
}
