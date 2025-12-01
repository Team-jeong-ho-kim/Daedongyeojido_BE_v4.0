package team.jeonghokim.daedongyeojido.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.schedule.domain.repository.ScheduleRepository;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.InterviewScheduleResponse;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.QueryInterviewScheduleListResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryInterviewScheduleListService {

    private final ScheduleRepository scheduleRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public QueryInterviewScheduleListResponse execute() {
        User user = userFacade.getCurrentUser();
        List<InterviewScheduleResponse> schedules = scheduleRepository.findAllSchedulesByClubId(user.getClub().getId());
        return new QueryInterviewScheduleListResponse(schedules);
    }
}
