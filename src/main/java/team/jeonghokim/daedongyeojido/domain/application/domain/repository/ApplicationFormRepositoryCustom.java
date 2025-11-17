package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormResponse;

import java.util.List;

public interface ApplicationFormRepositoryCustom {

    List<ApplicationFormResponse> findAllByClubId(Long clubId);
}
