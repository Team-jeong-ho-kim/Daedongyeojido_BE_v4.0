package team.jeonghokim.daedongyeojido.domain.announcement.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record OpenAnnouncementRequest(
        @NotNull(message = "지원서 폼 ID는 필수입니다.")
        Long applicationFormId
) {
}
