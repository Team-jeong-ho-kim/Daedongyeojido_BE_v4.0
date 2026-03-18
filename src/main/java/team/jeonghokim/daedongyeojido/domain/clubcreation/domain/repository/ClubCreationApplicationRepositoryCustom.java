package team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository;

import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationSummaryResponse;

import java.util.List;

public interface ClubCreationApplicationRepositoryCustom {

    List<ClubCreationApplicationSummaryResponse> findAllSummary();

    List<ClubCreationApplicationSummaryResponse> findAllSummaryByTeacherId(Long teacherId);
}
