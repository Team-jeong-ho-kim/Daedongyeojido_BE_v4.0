package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;

import java.util.List;

public interface AlarmRepositoryCustom {

    List<AlarmResponse> findAllByClubId(Long clubId);

    List<AlarmResponse> findAllByUserId(Long userId);
}
