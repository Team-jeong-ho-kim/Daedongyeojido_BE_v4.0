package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import lombok.Builder;

@Builder
public record SchedulerSmsPayload(

        Long submissionId,
        String phoneNumber,
        boolean isPassed,
        String clubName
) {
}
