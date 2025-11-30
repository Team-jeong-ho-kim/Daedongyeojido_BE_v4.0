package team.jeonghokim.daedongyeojido.domain.schedule.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.schedule.service.DecideInterviewScheduleService;
import team.jeonghokim.daedongyeojido.domain.schedule.service.UpdateInterviewScheduleService;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final DecideInterviewScheduleService decideInterviewScheduleService;
    private final UpdateInterviewScheduleService updateInterviewScheduleService;

    @PostMapping("/{user-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void decideInterviewSchedule(@PathVariable("user-id") Long userId, @RequestBody @Valid InterviewScheduleRequest request) {
        decideInterviewScheduleService.execute(userId, request);
    }

    @PatchMapping("/{schedule-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInterviewSchedule(@PathVariable("schedule-id") Long scheduleId, @RequestBody @Valid InterviewScheduleRequest request) {
        updateInterviewScheduleService.execute(scheduleId, request);
    }
}
