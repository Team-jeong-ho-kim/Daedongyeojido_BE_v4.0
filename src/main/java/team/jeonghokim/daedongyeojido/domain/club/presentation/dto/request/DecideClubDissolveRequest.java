package team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record DecideClubDissolveRequest(
        @NotNull(message = "해체 신청 수락 및 거절 여부는 null일 수 없습니다.")
        Boolean isDecision
) {
}
