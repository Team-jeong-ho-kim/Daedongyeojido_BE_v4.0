package team.jeonghokim.daedongyeojido.domain.schedule.domain.repository;

import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.InterviewScheduleResponse;

import java.util.List;

interface ScheduleRepositoryCustom {

    List<InterviewScheduleResponse> findAllSchedulesByClubId(Long clubId);
}
