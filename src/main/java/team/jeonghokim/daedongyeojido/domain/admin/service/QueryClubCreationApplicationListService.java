package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.vo.ClubVO;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryClubCreationApplicationListService {
    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public QueryClubListResponse execute() {

        List<ClubVO> clubs = clubRepository.findAllByCloseStatus();

        return QueryClubListResponse.from(clubs);
    }
}
