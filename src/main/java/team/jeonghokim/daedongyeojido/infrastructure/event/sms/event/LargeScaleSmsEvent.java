package team.jeonghokim.daedongyeojido.infrastructure.event.sms.event;

import lombok.Builder;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.payload.SchedulerSmsPayload;
import team.jeonghokim.daedongyeojido.infrastructure.sms.type.Message;

@Builder
public record LargeScaleSmsEvent(
        String phoneNumber,
        Message message,
        String clubName,
        SchedulerSmsPayload payload,
        ResultDuration resultDuration
) {
}
