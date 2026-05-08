package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateSubmitOnePagerService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QuerySubmitOnePagerListService;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;
    private final CreateSubmitOnePagerService createSubmitOnePagerService;
    private final QuerySubmitOnePagerListService querySubmitOnePagerListService;

    @PostMapping("/submissions/{submission-id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(
        @RequestBody @Valid CommentRequest request,
        @PathVariable("submission-id") Long submissionId
    ) {
        createRejectedOnePagerCommentService.execute(request, submissionId);
    }

    @PostMapping("/submissions/{form-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubmission(
        @ModelAttribute @Valid SubmitOnePagerRequest request,
        @PathVariable("form-id") Long formId
    ) {
        createSubmitOnePagerService.execute(request, formId);
    }

    @GetMapping("/submissions/my")
    @ResponseStatus(HttpStatus.OK)
    public OnePagerSubmissionListResponse queryMyOnePagerSubmission() {
        return querySubmitOnePagerListService.execute();
    }
}
