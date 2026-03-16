package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationReviewDecision;

import java.util.Objects;

public record UpsertClubCreationReviewRequest(
        @NotNull(message = "검토 결과는 필수입니다.")
        ClubCreationReviewDecision decision,

        String feedback
) {
    @AssertTrue(message = "수정 요청 또는 반려 시 피드백은 필수입니다.")
    public boolean isFeedbackValid() {
        if (decision == null || decision == ClubCreationReviewDecision.APPROVED) {
            return true;
        }

        return feedback != null && !feedback.isBlank();
    }
}
