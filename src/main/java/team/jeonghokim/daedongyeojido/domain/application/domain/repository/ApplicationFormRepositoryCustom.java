package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormListResponse;

import java.util.List;

public interface ApplicationFormRepositoryCustom {

    List<ApplicationFormListResponse> findAllByClubId(Long clubId);
}
