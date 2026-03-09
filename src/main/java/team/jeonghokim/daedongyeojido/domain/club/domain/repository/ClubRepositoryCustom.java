package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubDetailDto;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubMembersDto;

import java.util.List;
import java.util.Optional;

public interface ClubRepositoryCustom {

    List<ClubVO> findAllByIsOpenIsTrue();

    List<ClubMembersDto> findClubMembersById(Long clubId);

    Optional<ClubDetailDto> findClubDetailById(Long clubId);
}
