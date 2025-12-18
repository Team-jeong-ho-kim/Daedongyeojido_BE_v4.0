package team.jeonghokim.daedongyeojido.domain.admin.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.admin.service.DecideResultDurationService;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.DecideClubDissolveRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubCreationService;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubDissolveService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DecideClubCreationService decideClubCreationService;
    private final DecideClubDissolveService decideClubDissolveService;
    private final DecideResultDurationService decideResultDurationService;

    @PatchMapping("/club/create/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClubCreation(@PathVariable("club-id") Long clubId, @RequestBody @Valid DecideClubCreationRequest request) {
        decideClubCreationService.execute(clubId, request);
    }

    @DeleteMapping("/dissolution/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClubDissolve(@PathVariable("club-id") Long clubId, @RequestBody @Valid DecideClubDissolveRequest request) {
        decideClubDissolveService.execute(clubId, request);
    }

    @PostMapping("/duration")
    @ResponseStatus(HttpStatus.CREATED)
    public void decideResultDuration(@RequestBody @Valid DecideResultDurationRequest request) {
        decideResultDurationService.execute(request);
    }
}
