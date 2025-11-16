package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormListResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;
    private final ClubFacade clubFacade;

    @Transactional(readOnly = true)
    public List<ApplicationFormListResponse> execute(Long clubId) {

        Club club = clubFacade.getClubById(clubId);

        return applicationFormRepository.findAllByClubId(club.getId())
                .stream()
                .map(ApplicationFormListResponse::of)
                .toList();
    }
}
