package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;

import java.util.List;

public interface ClubCustomRepository {

    List<QueryClubListResponse.ClubDto> findAllByIsOpenIsTrue();
}
