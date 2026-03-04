package team.jeonghokim.daedongyeojido.domain.clubcreationform.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.presentation.dto.response.ClubCreationFormResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreationform.service.QueryClubCreationFormService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club-creation-form")
public class ClubCreationFormController {

    private final QueryClubCreationFormService queryClubCreationFormService;

    @GetMapping("/{club-creation-form}")
    @ResponseStatus(HttpStatus.OK)
    public ClubCreationFormResponse queryClubCreationForm(@PathVariable("club-creation-form") Long clubCreationFormId) {
        return queryClubCreationFormService.execute(clubCreationFormId);
    }
}
