package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;

import java.util.List;

public interface ClubRepositoryCustom {

    List<ClubVO> findAllByIsOpenIsTrue();
}
