package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerSubmissionListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryOnePagerSubmissionResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryListSubmitOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.UserOnePagerDetailResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.service.*;

import java.util.List;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateSubmitOnePagerService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QuerySubmitOnePagerListService;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;
    private final QueryOnePagerListService queryOnePagerListService;
    private final CreateSubmitOnePagerService createSubmitOnePagerService;
    private final QueryDetailUserOnePagerService queryDetailUserOnePagerService;
    private final QuerySubmitOnePagerService querySubmitOnePagerService;
    private final CancelSubmitService cancelSubmitService;
    private final QuerySubmitOnePagerListService querySubmitOnePagerListService;
    private final QueryMyOnePagerSubmissionDetailService queryMyOnePagerSubmissionDetailService;

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

    @GetMapping("/forms/{form-id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOnePagerDetailResponse queryUserOnePager(
        @PathVariable("form-id") Long formId
    ) {
        return queryDetailUserOnePagerService.execute(formId);
    }

    @GetMapping("/submissions/{form-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryListSubmitOnePagerResponse queryListOnePager(
        @PathVariable("form-id") Long formId
    ) {
        return querySubmitOnePagerService.execute(formId);
    }

    @GetMapping("/submissions/my")
    @ResponseStatus(HttpStatus.OK)
    public OnePagerSubmissionListResponse queryMyOnePagerSubmission() {
        return querySubmitOnePagerListService.execute();
    }

    @PatchMapping("/submissions/{submission-id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSubmit(@PathVariable(name = "submission-id") Long submissionId) {
        cancelSubmitService.execute(submissionId);
    }

    @GetMapping("/submissions/{submission-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryOnePagerSubmissionResponse queryMyOnePagerSubmissionDetail(@PathVariable(name = "submission-id") Long submissionId) {
        return queryMyOnePagerSubmissionDetailService.execute(submissionId);
    }
}
