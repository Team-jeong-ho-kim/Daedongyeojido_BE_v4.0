package team.jeonghokim.daedongyeojido.domain.club.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;

import java.util.List;

public interface ClubCustomRepository {

    List<Club> findAllByIsOpenIsTrue();
}
