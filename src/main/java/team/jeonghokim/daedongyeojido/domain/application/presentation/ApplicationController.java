package team.jeonghokim.daedongyeojido.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.user.service.CancelApplicationService;
import team.jeonghokim.daedongyeojido.domain.user.service.CreateApplicationService;
import team.jeonghokim.daedongyeojido.domain.user.service.DeleteApplicationService;
import team.jeonghokim.daedongyeojido.domain.user.service.QueryApplicationDetailService;
import team.jeonghokim.daedongyeojido.domain.user.service.QueryApplicationListService;
import team.jeonghokim.daedongyeojido.domain.user.service.SubmitApplicationService;
import team.jeonghokim.daedongyeojido.domain.user.service.UpdateApplicationService;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final CreateApplicationService createApplicationService;
    private final QueryApplicationDetailService queryApplicationDetailService;
    private final QueryApplicationListService queryApplicationListService;
    private final UpdateApplicationService updateApplicationService;
    private final DeleteApplicationService deleteApplicationService;
    private final SubmitApplicationService submitApplicationService;
    private final CancelApplicationService cancelApplicationService;

    @PostMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createApplication(@PathVariable("application-form-id") Long applicationFormId, @RequestBody @Valid SubmissionRequest request) {
        createApplicationService.execute(applicationFormId, request);
    }

    @GetMapping("/{submission-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationDetailResponse queryApplicationDetail(@PathVariable("submission-id") Long submissionId) {
        return queryApplicationDetailService.execute(submissionId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryApplicationListResponse queryApplicationList() {
        return queryApplicationListService.execute();
    }

    @PatchMapping("/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplication(@PathVariable("submission-id") Long submissionId, @RequestBody @Valid SubmissionRequest request) {
        updateApplicationService.execute(submissionId, request);
    }

    @DeleteMapping("/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplication(@PathVariable("submission-id") Long submissionId) {
        deleteApplicationService.execute(submissionId);
    }

    @PatchMapping("/submit/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitApplication(@PathVariable("submission-id") Long submissionId) {
        submitApplicationService.execute(submissionId);
    }

    @PatchMapping("/cancel/{submission-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelApplication(@PathVariable("submission-id") Long submissionId) {
        cancelApplicationService.execute(submissionId);
    }
}
