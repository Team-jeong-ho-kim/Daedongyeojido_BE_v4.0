package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record DecideClubCreationRequest(
        @NotNull(message = "개설 신청 수락 및 거절 여부는 null일 수 없습니다.")
        Boolean isApproved
) {}
