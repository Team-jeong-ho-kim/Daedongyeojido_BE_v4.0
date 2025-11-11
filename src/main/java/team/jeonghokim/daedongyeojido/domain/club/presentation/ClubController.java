package team.jeonghokim.daedongyeojido.domain.club.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.CreateClubService;
import team.jeonghokim.daedongyeojido.domain.club.service.DissolveClubService;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubDetailService;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubListService;
import team.jeonghokim.daedongyeojido.domain.club.service.UpdateClubService;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final CreateClubService createClubService;
    private final QueryClubListService queryClubListService;
    private final QueryClubDetailService queryClubDetailService;
    private final UpdateClubService updateClubService;
    private final DissolveClubService dissolveClubService;

    @PostMapping("/create/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(@ModelAttribute @Valid ClubRequest request) {
        createClubService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryClubListResponse queryClubList() {
        return queryClubListService.execute();
    }

    @GetMapping("/{club-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryClubDetailResponse queryClubDetail(@PathVariable("club-id") Long clubId) {
        return queryClubDetailService.execute(clubId);
    }

    @PatchMapping("/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClub(@PathVariable("club-id") Long clubId, @ModelAttribute @Valid ClubRequest request) {
        updateClubService.execute(clubId, request);
    }

    @PostMapping("/dissolution")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissolveClub() {
        dissolveClubService.execute();
    }
}
