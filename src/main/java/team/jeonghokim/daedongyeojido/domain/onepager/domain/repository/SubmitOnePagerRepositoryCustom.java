package team.jeonghokim.daedongyeojido.domain.onepager.domain.repository;

import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;

import java.util.List;

public interface SubmitOnePagerRepositoryCustom {
    List<SubmitOnePager> findByClub(Club club);
}
