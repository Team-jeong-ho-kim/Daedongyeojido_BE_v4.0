package team.jeonghokim.daedongyeojido.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormDetailResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QueryApplicationFormListResponse;
import team.jeonghokim.daedongyeojido.domain.application.service.CreateApplicationFormService;
import team.jeonghokim.daedongyeojido.domain.application.service.QueryApplicationFormListService;
import team.jeonghokim.daedongyeojido.domain.application.service.QueryApplicationFormDetailService;
import team.jeonghokim.daedongyeojido.domain.application.service.UpdateApplicationFormService;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final CreateApplicationFormService createApplicationFormService;
    private final QueryApplicationFormDetailService queryApplicationFormDetailService;
    private final QueryApplicationFormListService queryAllApplicationFormService;
    private final UpdateApplicationFormService updateApplicationFormService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createApplicationForm(@RequestBody @Valid ApplicationFormRequest request) {
        createApplicationFormService.execute(request);
    }

    @GetMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationFormDetailResponse queryApplicationForm(@PathVariable("application-form-id") Long applicationFormId) {
        return queryApplicationFormDetailService.execute(applicationFormId);
    }

    @GetMapping("/all/{club-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationFormListResponse queryAllApplicationForm(@PathVariable("club-id") Long clubId) {
        return queryAllApplicationFormService.execute(clubId);
    }

    @PatchMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplicationForm(@PathVariable("application-form-id") Long applicationFormId, @RequestBody @Valid ApplicationFormRequest request) {
        updateApplicationFormService.execute(applicationFormId, request);
    }
}
