package team.jeonghokim.daedongyeojido.domain.admin.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideClubCreationRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.DecideClubCreationService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DecideClubCreationService decideClubCreationService;

    @PatchMapping("/club/create/{club-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decideClubCreation(@PathVariable("club-id") Long clubId, @RequestBody @Valid DecideClubCreationRequest request) {
        decideClubCreationService.execute(clubId, request);
    }
}
