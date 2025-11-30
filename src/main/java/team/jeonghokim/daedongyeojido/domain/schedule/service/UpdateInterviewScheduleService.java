package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleNotFoundException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;

@Service
@RequiredArgsConstructor
public class UpdateInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void execute(Long scheduleId, InterviewScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> InterviewScheduleNotFoundException.EXCEPTION);

        schedule.updateSchedule(request);
    }
}
