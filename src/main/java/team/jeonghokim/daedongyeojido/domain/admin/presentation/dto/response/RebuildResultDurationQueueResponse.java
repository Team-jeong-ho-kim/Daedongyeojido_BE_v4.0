package team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response;

import lombok.Builder;

@Builder
public record RebuildResultDurationQueueResponse(
        long deletedSmsZsetCount,
        long deletedAlarmZsetCount,
        int restoredSmsCount,
        int restoredAlarmCount,
        int skippedCount
) {
}
