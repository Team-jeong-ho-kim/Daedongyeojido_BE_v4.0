package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.enums.Status;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationAlreadySetException;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.service.SchedulerService;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class DecideResultDurationService {

    private final ResultDurationRepository resultDurationRepository;
    private final TaskScheduler taskScheduler;
    private final SchedulerService schedulerService;

    private static final String TIME_ZONE = "Asia/Seoul";

    @Transactional
    public void execute(DecideResultDurationRequest request) {

        if (resultDurationRepository.existsBySmsStatusOrAlarmStatus(Status.PENDING, Status.PENDING)) {
            throw ResultDurationAlreadySetException.EXCEPTION;
        }

        ResultDuration resultDuration = resultDurationRepository
                .save(new ResultDuration(request.resultDuration()));

        Instant executeTime = resultDuration.getResultDurationTime()
                .atZone(ZoneId.of(TIME_ZONE))
                .toInstant();

        taskScheduler.schedule(schedulerService::execute, executeTime);
    }

    public void executeSmsScheduler(ResultDuration resultDuration) {

        resultDuration.smsRequested();
    }

    public void executeAlarmScheduler(ResultDuration resultDuration) {

        resultDuration.alarmRequested();
    }
}
