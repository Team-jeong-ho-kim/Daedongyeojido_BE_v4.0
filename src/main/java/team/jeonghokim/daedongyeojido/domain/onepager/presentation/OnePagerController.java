package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import com.solapi.shadow.retrofit2.http.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.OnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.UserOnePagerDetailResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QueryDetailUserOnePagerService;
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
    private final QueryDetailUserOnePagerService queryDetailUserOnePagerService;

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

}
