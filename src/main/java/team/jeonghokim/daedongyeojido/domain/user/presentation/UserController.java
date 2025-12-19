package team.jeonghokim.daedongyeojido.domain.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryUserSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.service.QueryUserSubmissionListService;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideTeamMemberApplicationRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.UpdateMyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryMyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.user.service.DecideClubService;
import team.jeonghokim.daedongyeojido.domain.user.service.DecideTeamMemberApplicationService;
import team.jeonghokim.daedongyeojido.domain.user.service.InputMyInfoService;
import team.jeonghokim.daedongyeojido.domain.user.service.QueryMyInfoService;
import team.jeonghokim.daedongyeojido.domain.user.service.UpdateMyInfoService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final InputMyInfoService inputMyInfoService;
    private final QueryMyInfoService queryMyInfoService;
    private final UpdateMyInfoService updateMyInfoService;
    private final DecideTeamMemberApplicationService decideTeamMemberApplicationService;
    private final QueryUserSubmissionListService queryUserSubmissionListService;
    private final DecideClubService decideClubService;

    @PatchMapping("/my-info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inputMyInfo(@ModelAttribute @Valid MyInfoRequest request) {
        inputMyInfoService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryMyInfoResponse queryMyInfo() {
        return queryMyInfoService.execute();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMyInfo(@ModelAttribute @Valid UpdateMyInfoRequest request) {
        updateMyInfoService.execute(request);
    }

    @PatchMapping("/members")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveTeamMember(@RequestBody @Valid DecideTeamMemberApplicationRequest request) {
        decideTeamMemberApplicationService.execute(request);
    }

    @GetMapping("/submissions")
    @ResponseStatus(HttpStatus.OK)
    public QueryUserSubmissionListResponse queryUserSubmissionList() {
        return queryUserSubmissionListService.execute();
    }

    @PatchMapping("/submissions/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClub(@PathVariable("submission-id") Long submissionId, @RequestBody @Valid DecideClubRequest request) {
        decideClubService.execute(submissionId, request);
    }
}
