package team.jeonghokim.daedongyeojido.domain.admin.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.UploadClubCreationFormRequest;
import team.jeonghokim.daedongyeojido.domain.admin.service.*;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.ClubCreationInformationResponse;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.response.QueryClubListResponse;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubCreationService;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubDissolveService;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.UpdateResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.QueryClubCreationInformationService;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateTeacherRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.service.CreateTeacherService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DecideClubCreationService decideClubCreationService;
    private final DecideClubDissolveService decideClubDissolveService;
    private final DecideResultDurationService decideResultDurationService;
    private final UpdateResultDurationService updateResultDurationService;
    private final UploadClubCreationFormService uploadClubCreationFormService;
    private final QueryClubCreationInformationService queryClubCreationInformationService;
    private final QueryClubCreationApplicationListService queryClubCreationApplicationListService;
    private final DeleteClubCreationFormService deleteClubCreationFormService;
    private final DeleteResultDurationService deleteResultDurationService;
    private final CreateTeacherService createTeacherService;

    @PatchMapping("/clubs/applications/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClubCreation(@PathVariable("club-id") Long clubId, @RequestBody @Valid DecideClubCreationRequest request) {
        decideClubCreationService.execute(clubId, request);
    }

    @DeleteMapping("/dissolution/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClubDissolve(@PathVariable("club-id") Long clubId, @RequestBody @Valid DecideClubDissolveRequest request) {
        decideClubDissolveService.execute(clubId, request);
    }

    @PostMapping("/result-duration")
    @ResponseStatus(HttpStatus.CREATED)
    public void decideResultDuration(@RequestBody @Valid DecideResultDurationRequest request) {
        decideResultDurationService.execute(request);
    }

    @PatchMapping("/result-duration/{result-duration-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResultDuration(@PathVariable("result-duration-id") Long resultDurationId, @RequestBody @Valid UpdateResultDurationRequest request) {
        updateResultDurationService.execute(resultDurationId, request);
    }

    @PostMapping("/club-creation-form")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadClubCreationForm(@ModelAttribute @Valid UploadClubCreationFormRequest request) {
        uploadClubCreationFormService.execute(request);
    }

    @GetMapping("/club-creation-form/{club-id}")
    @ResponseStatus(HttpStatus.OK)
    public ClubCreationInformationResponse queryClubCreationInformation(@PathVariable("club-id") Long clubId) {
        return queryClubCreationInformationService.execute(clubId);
    }

    @GetMapping("/club-creation-application")
    @ResponseStatus(HttpStatus.OK)
    public QueryClubListResponse queryClubCreationApplication() {
        return queryClubCreationApplicationListService.execute();
    }

    @DeleteMapping("/club-creation-form/{club-creation-form}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClubCreationForm(@PathVariable("club-creation-form") Long clubCreationFormId) {
        deleteClubCreationFormService.execute(clubCreationFormId);
    }

    @DeleteMapping("/result-duration/{result-duration-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResultDuration(@PathVariable("result-duration-id") Long resultDurationId) {
        deleteResultDurationService.execute(resultDurationId);
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTeacher(@RequestBody @Valid CreateTeacherRequest request) {
        createTeacherService.execute(request);
    }
}
