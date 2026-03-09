package team.jeonghokim.daedongyeojido.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.request.ApplicationFormRequest;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QueryApplicationFormDetailResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QueryApplicationFormListResponse;
import team.jeonghokim.daedongyeojido.domain.application.service.*;

@RestController
@RequestMapping("/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {
    private final CreateApplicationFormService createApplicationFormService;
    private final QueryApplicationFormDetailService queryApplicationFormDetailService;
    private final QueryApplicationFormListService queryAllApplicationFormService;
    private final UpdateApplicationFormService updateApplicationFormService;
    private final DeleteApplicationFormService deleteApplicationFormService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createApplicationForm(@RequestBody @Valid ApplicationFormRequest request) {
        createApplicationFormService.execute(request);
    }

    @GetMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationFormDetailResponse queryApplicationForm(@PathVariable("application-form-id") Long applicationFormId) {
        return queryApplicationFormDetailService.execute(applicationFormId);
    }

    @GetMapping("/clubs/{club-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationFormListResponse queryAllApplicationForm(@PathVariable("club-id") Long clubId) {
        return queryAllApplicationFormService.execute(clubId);
    }

    @PatchMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplicationForm(@PathVariable("application-form-id") Long applicationFormId, @RequestBody @Valid ApplicationFormRequest request) {
        updateApplicationFormService.execute(applicationFormId, request);
    }

    @DeleteMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplicationForm(@PathVariable("application-form-id") Long applicationFormId) {
        deleteApplicationFormService.execute(applicationFormId);
    }
}
