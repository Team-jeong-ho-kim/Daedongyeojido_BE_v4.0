package team.jeonghokim.daedongyeojido.domain.club.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.TeamMemberRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.*;

@RestController
@RequestMapping("/club")
@RequiredArgsConstructor
public class ClubController {

    private final CreateClubService createClubService;
    private final QueryClubListService queryClubListService;
    private final QueryClubDetailService queryClubDetailService;
    private final UpdateClubService updateClubService;
    private final ApplyTeamMemberService applyTeamMemberService;
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

    @PostMapping("/member/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void applyMember(@RequestBody @Valid TeamMemberRequest request) {
        applyTeamMemberService.execute(request);
    }

    @PostMapping("/dissolution")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissolveClub() {
        dissolveClubService.execute();
    }
}
