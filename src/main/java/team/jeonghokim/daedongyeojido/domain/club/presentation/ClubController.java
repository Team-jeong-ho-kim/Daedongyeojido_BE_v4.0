package team.jeonghokim.daedongyeojido.domain.club.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.PassClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.TeamMemberRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.UpdateClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubDetailResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.*;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.CreateClubCreationApplicationService;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryClubSubmissionDetailResponse;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.response.QueryClubSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.submission.service.QueryClubSubmissionDetailService;
import team.jeonghokim.daedongyeojido.domain.submission.service.QueryClubSubmissionListService;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final CreateClubCreationApplicationService createClubCreationApplicationService;
    private final ApplyTeamMemberService applyTeamMemberService;
    private final DissolveClubService dissolveClubService;
    private final QueryClubListService queryClubListService;
    private final QueryClubDetailService queryClubDetailService;
    private final QueryClubSubmissionListService queryClubSubmissionListService;
    private final QueryClubSubmissionDetailService queryClubSubmissionDetailService;
    private final UpdateClubService updateClubService;
    private final PassClubService passClubService;
    private final CompleteInterviewService completeInterviewService;
    private final DeleteTeamMemberService deleteTeamMemberService;
    private final DeleteClubAlarmService deleteClubAlarmService;

    @PostMapping("/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(@ModelAttribute @Valid CreateClubRequest request) {
        createClubCreationApplicationService.execute(request);
    }

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void applyMember(@RequestBody @Valid TeamMemberRequest request) {
        applyTeamMemberService.execute(request);
    }

    @PostMapping("/dissolution")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissolveClub() {
        dissolveClubService.execute();
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

    @GetMapping("/submissions/all/{application-form-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryClubSubmissionListResponse querySubmissionList(@PathVariable("application-form-id") Long applicationFormId) {
        return queryClubSubmissionListService.execute(applicationFormId);
    }

    @GetMapping("/submissions/{submission-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryClubSubmissionDetailResponse querySubmissionDetail(@PathVariable("submission-id") Long submissionId) {
        return queryClubSubmissionDetailService.execute(submissionId);
    }

    @PatchMapping("/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClub(@PathVariable("club-id") Long clubId, @ModelAttribute @Valid UpdateClubRequest request) {
        updateClubService.execute(clubId, request);
    }

    @PatchMapping("/pass/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passClub(@PathVariable("submission-id") Long submissionId, @RequestBody @Valid PassClubRequest request) {
        passClubService.execute(submissionId, request);
    }

    @PatchMapping("/interviews/{submission-id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeInterview(@PathVariable("submission-id") Long submissionId) {
        completeInterviewService.execute(submissionId);
    }

    @DeleteMapping("/members/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeamMember(@PathVariable("user-id") Long userId) {
        deleteTeamMemberService.execute(userId);
    }

    @DeleteMapping("/alarms/{alarm-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClubAlarm(@PathVariable("alarm-id") Long alarmId) {
        deleteClubAlarmService.execute(alarmId);
    }
}
