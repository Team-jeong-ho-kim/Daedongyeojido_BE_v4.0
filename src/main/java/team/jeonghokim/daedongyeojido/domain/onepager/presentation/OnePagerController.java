package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.CommentRequest;
import team.jeonghokim.daedongyeojido.domain.onepager.service.CreateRejectedOnePagerCommentService;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QueryOnePagerListService;

import java.util.List;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {
    private final CreateRejectedOnePagerCommentService createRejectedOnePagerCommentService;
    private final QueryOnePagerListService queryOnePagerListService;

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
}
