package team.jeonghokim.daedongyeojido.infrastructure.event.domain.user;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Builder
public record LargeScaleSmsEvent(
        String phoneNumber,
        Message message,
        String clubName,
        SchedulerPayload payload,
        ResultDuration resultDuration
) {
}
