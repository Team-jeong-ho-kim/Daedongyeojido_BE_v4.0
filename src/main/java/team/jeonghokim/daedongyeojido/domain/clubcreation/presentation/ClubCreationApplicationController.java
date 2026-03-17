package team.jeonghokim.daedongyeojido.domain.clubcreation.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request.UpdateClubCreationApplicationRequest;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.request.UpsertClubCreationReviewRequest;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationDetailResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.QueryClubCreationApplicationListResponse;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.QueryClubCreationApplicationDetailService;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.QueryMyClubCreationApplicationService;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.QueryClubCreationReviewListService;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.SubmitClubCreationApplicationService;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.UpdateClubCreationApplicationService;
import team.jeonghokim.daedongyeojido.domain.clubcreation.service.UpsertClubCreationReviewService;

@RestController
@RequestMapping("/club-creation-applications")
@RequiredArgsConstructor
public class ClubCreationApplicationController {

    private final QueryMyClubCreationApplicationService queryMyClubCreationApplicationService;
    private final UpdateClubCreationApplicationService updateClubCreationApplicationService;
    private final SubmitClubCreationApplicationService submitClubCreationApplicationService;
    private final QueryClubCreationReviewListService queryClubCreationReviewListService;
    private final QueryClubCreationApplicationDetailService queryClubCreationApplicationDetailService;
    private final UpsertClubCreationReviewService upsertClubCreationReviewService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ClubCreationApplicationDetailResponse queryMyApplication() {
        return queryMyClubCreationApplicationService.execute();
    }

    @PatchMapping("/{application-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplication(
            @PathVariable("application-id") Long applicationId,
            @ModelAttribute @Valid UpdateClubCreationApplicationRequest request
    ) {
        updateClubCreationApplicationService.execute(applicationId, request);
    }

    @PostMapping("/{application-id}/submit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitApplication(@PathVariable("application-id") Long applicationId) {
        submitClubCreationApplicationService.execute(applicationId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryClubCreationApplicationListResponse queryApplications() {
        return queryClubCreationReviewListService.execute();
    }

    @GetMapping("/{application-id}")
    @ResponseStatus(HttpStatus.OK)
    public ClubCreationApplicationDetailResponse queryApplicationDetail(@PathVariable("application-id") Long applicationId) {
        return queryClubCreationApplicationDetailService.execute(applicationId);
    }

    @PutMapping("/{application-id}/review")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reviewApplication(
            @PathVariable("application-id") Long applicationId,
            @RequestBody @Valid UpsertClubCreationReviewRequest request
    ) {
        upsertClubCreationReviewService.execute(applicationId, request);
    }
}
