package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.infrastructure.scheduler.service.SchedulerService;

@Service
@RequiredArgsConstructor
public class DispatchAllResultDurationQueueService {

    private final SchedulerService schedulerService;

    @Transactional
    public void execute() {
        schedulerService.executeAllFromQueue();
    }
}
