package team.jeonghokim.daedongyeojido.domain.club.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.CreateClubService;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubDetailService;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubListService;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final CreateClubService createClubService;
    private final QueryClubListService queryClubListService;
    private final QueryClubDetailService queryClubDetailService;

    @PostMapping("/create/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(@RequestBody @Valid CreateClubRequest request) {
        createClubService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryClubListResponse queryClubList() {
        return queryClubListService.execute();
    }

    @GetMapping("/{club-id}")
    public QueryClubDetailResponse queryClubDetail(@PathVariable("club-id") Long clubId) {
        return queryClubDetailService.execute(clubId);
    }
}
