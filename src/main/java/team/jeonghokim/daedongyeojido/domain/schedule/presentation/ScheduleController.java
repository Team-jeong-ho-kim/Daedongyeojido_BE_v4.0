package team.jeonghokim.daedongyeojido.domain.schedule.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.DecideInterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.schedule.service.DecideInterviewScheduleService;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final DecideInterviewScheduleService decideInterviewScheduleService;

    @PostMapping("/{user-id}")
    public void decideInterviewSchedule(@PathVariable("user-id") Long userId, @RequestBody DecideInterviewScheduleRequest request) {
        decideInterviewScheduleService.execute(userId, request);
    }
}
