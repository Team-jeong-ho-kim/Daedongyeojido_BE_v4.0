package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import lombok.Builder;

@Builder
public record SchedulerPayload(
        Long submissionId,
        String phoneNumber,
        boolean isPassed
) {
}
