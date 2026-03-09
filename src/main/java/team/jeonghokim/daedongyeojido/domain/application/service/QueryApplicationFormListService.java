package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QueryApplicationFormListResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryApplicationFormListService {
    private final ApplicationFormRepository applicationFormRepository;
    private final ClubFacade clubFacade;

    @Transactional(readOnly = true)
    public QueryApplicationFormListResponse execute(Long clubId) {
        Club club = clubFacade.getClubById(clubId);
        List<ApplicationFormResponse> applicationForms = applicationFormRepository.findAllByClubId(club.getId());

        return QueryApplicationFormListResponse.from(applicationForms);
    }
}
