package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerSubmissionsResponse;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuerySubmitOnePagerListService {
    private final SubmitOnePagerRepository submitOnePagerRepository;
    private final UserFacade userFacade;

    public OnePagerSubmissionListResponse execute() {
        Club club = userFacade.getCurrentUser().getClub();

        List<SubmitOnePager> submissions = submitOnePagerRepository.findByClub(club);
        List<OnePagerSubmissionsResponse> onePagerSubmissionsResponses = submissions.stream()
                .map(submitOnePager -> OnePagerSubmissionsResponse.from(submitOnePager))
                .toList();
        return new OnePagerSubmissionListResponse(onePagerSubmissionsResponses);
    }
}
