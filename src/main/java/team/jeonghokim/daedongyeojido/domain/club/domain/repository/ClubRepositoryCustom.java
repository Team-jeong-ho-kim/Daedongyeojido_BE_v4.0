package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;

import java.util.List;
import java.util.Optional;

public interface ClubRepositoryCustom {

    List<ClubVO> findAllByIsOpenIsTrue();

    Optional<QueryClubDetailResponse> findDetailWithMembersById(Long clubId);
}
