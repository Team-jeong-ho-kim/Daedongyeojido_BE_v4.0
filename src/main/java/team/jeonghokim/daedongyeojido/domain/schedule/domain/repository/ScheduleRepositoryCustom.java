package team.jeonghokim.daedongyeojido.domain.schedule.domain.repository;

import team.jeonghokim.daedongyeojido.domain.schedule.domain.Schedule;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.InterviewScheduleResponse;

import java.util.List;
import java.util.Optional;

interface ScheduleRepositoryCustom {

    List<InterviewScheduleResponse> findAllSchedulesByClubId(Long clubId);

    Optional<Schedule> findScheduleById(Long scheduleId);
}
