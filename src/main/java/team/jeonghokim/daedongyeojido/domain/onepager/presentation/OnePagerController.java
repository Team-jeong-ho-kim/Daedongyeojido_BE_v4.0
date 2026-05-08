package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CancelSubmitService;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QueryOnePagerListService;

import java.util.List;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateSubmitOnePagerService;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;
    private final QueryOnePagerListService queryOnePagerListService;
    private final CreateSubmitOnePagerService createSubmitOnePagerService;
    private final CancelSubmitService cancelSubmitService;

    @GetMapping("/forms")
    @ResponseStatus(HttpStatus.OK)
    public OnePagerListResponse queryOnePagerList() {
        List<OnePagerResponse> onePagers = queryOnePagerListService.execute();
        return OnePagerListResponse.from(onePagers);
    }

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

    @PatchMapping("/submissions/{submission-id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSubmit(@PathVariable(name = "submission-id") Long submissionId) {
        cancelSubmitService.execute(submissionId);
    }
}
