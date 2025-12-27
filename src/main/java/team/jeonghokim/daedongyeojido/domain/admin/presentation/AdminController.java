package team.jeonghokim.daedongyeojido.domain.admin.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubCreationService;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubDissolveService;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.ResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.service.UpdateResultDurationService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DecideClubCreationService decideClubCreationService;
    private final DecideClubDissolveService decideClubDissolveService;
    private final DecideResultDurationService decideResultDurationService;
    private final UpdateResultDurationService updateResultDurationService;

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

    @PatchMapping("result-duration/{result-duration-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResultDuration(@PathVariable("result-duration-id") Long resultDurationId, @RequestBody @Valid ResultDurationRequest request) {
        updateResultDurationService.execute(resultDurationId, request);
    }
    
}
