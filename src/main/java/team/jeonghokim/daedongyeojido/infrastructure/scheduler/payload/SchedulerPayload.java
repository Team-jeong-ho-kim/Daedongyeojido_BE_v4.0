package team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SchedulerPayload(
        @JsonProperty("submission_id") Long submissionId,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("is_passed") boolean isPassed
) {
}
