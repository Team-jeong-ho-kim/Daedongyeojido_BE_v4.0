package team.jeonghokim.daedongyeojido.domain.application.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationDetailResponse;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.QueryApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.user.service.*;

@Service
@RequestMapping("/application")
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
