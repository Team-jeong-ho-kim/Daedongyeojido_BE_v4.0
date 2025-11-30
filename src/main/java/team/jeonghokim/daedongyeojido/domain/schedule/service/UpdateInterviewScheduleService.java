package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleAccessDeniedException;
import team.jeonghokim.daedongyeojido.domain.schedule.exception.InterviewScheduleNotFoundException;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.request.InterviewScheduleRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class UpdateInterviewScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long scheduleId, InterviewScheduleRequest request) {
        User currentUser = userFacade.getCurrentUser();
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> InterviewScheduleNotFoundException.EXCEPTION);

        if (!(currentUser.getClub().getId().equals(schedule.getClub().getId()))) {
            throw InterviewScheduleAccessDeniedException.EXCEPTION;
        }

        schedule.updateSchedule(request);
    }
}
