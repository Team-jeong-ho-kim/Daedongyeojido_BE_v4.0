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
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionDetailResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QuerySubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.service.QueryClubSubmissionDetailService;
import team.jeonghokim.daedongyeojido.domain.submission.service.QuerySubmissionListService;

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
    private final DeleteTeamMemberService deleteTeamMemberService;
    private final QuerySubmissionListService querySubmissionListService;
    private final QueryClubSubmissionDetailService queryClubSubmissionDetailService;

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

    @DeleteMapping("/member/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeamMember(@PathVariable("user-id") Long userId) {
        deleteTeamMemberService.execute(userId);
    }

    @GetMapping("/submission")
    @ResponseStatus(HttpStatus.OK)
    public QuerySubmissionListResponse querySubmissionList() {
        return querySubmissionListService.execute();
    }

    @GetMapping("/submission/{submission-id}")
    @ResponseStatus(HttpStatus.OK)
    public QuerySubmissionDetailResponse querySubmissionDetail(@PathVariable("submission-id") Long submissionId) {
        return queryClubSubmissionDetailService.execute(submissionId);
    }
}
