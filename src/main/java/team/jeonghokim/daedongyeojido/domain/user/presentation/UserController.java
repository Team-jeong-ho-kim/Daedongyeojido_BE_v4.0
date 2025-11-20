package team.jeonghokim.daedongyeojido.domain.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.DecideTeamMemberApplicationRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.UpdateMyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryMyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.user.service.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final InputMyInfoService inputMyInfoService;
    private final QueryMyInfoService queryMyInfoService;
    private final UpdateMyInfoService updateMyInfoService;
    private final DecideTeamMemberApplicationService decideTeamMemberApplicationService;
    private final CreateApplicationService createApplicationService;
    private final QueryApplicationDetailService queryApplicationDetailService;

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

    @PatchMapping("/member/decision")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveTeamMember(@RequestBody @Valid DecideTeamMemberApplicationRequest request) {
        decideTeamMemberApplicationService.execute(request);
    }

    @PostMapping("/application/{application-form-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubmission(@PathVariable("application-form-id") Long applicationFormId, @RequestBody @Valid SubmissionRequest request) {
        createApplicationService.execute(applicationFormId, request);
    }

    @GetMapping("/application/{submission-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationDetailResponse queryApplicationDetail(@PathVariable("submission-id") Long submissionId) {
        return queryApplicationDetailService.execute(submissionId);
    }
}
