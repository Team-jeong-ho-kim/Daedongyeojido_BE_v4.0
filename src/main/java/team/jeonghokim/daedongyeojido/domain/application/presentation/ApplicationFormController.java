package team.jeonghokim.daedongyeojido.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormDetailResponse;
import team.jeonghokim.daedongyeojido.domain.application.service.CreateApplicationFormService;
import team.jeonghokim.daedongyeojido.domain.application.service.QueryApplicationFormDetailService;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final CreateApplicationFormService createApplicationFormService;
    private final QueryApplicationFormDetailService queryApplicationFormDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createApplicationForm(@RequestBody @Valid ApplicationFormRequest request) {
        createApplicationFormService.execute(request);
    }

    @GetMapping("/{club-id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationFormDetailResponse queryApplicationForm(@PathVariable("club-id") Long clubId) {
        return queryApplicationFormDetailService.execute(clubId);
    }
}
