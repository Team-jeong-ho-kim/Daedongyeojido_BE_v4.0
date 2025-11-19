package team.jeonghokim.daedongyeojido.domain.submission.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.submission.presentation.dto.request.SubmissionRequest;
import team.jeonghokim.daedongyeojido.domain.submission.service.CreateSubmissionService;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final CreateSubmissionService createSubmissionService;

    @PostMapping("/{application-form-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubmission(@PathVariable("application-form-id") Long applicationFormId, @RequestBody @Valid SubmissionRequest request) {
        createSubmissionService.execute(applicationFormId, request);
    }
}
