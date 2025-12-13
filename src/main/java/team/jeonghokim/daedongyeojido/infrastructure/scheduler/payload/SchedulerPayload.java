package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

public record SchedulerPayload(
        Long submissionId,
        String phoneNumber,
        boolean isPassed
) {
}
