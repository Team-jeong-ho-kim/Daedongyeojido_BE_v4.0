package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryListSubmitOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.ChangeOnePagerStateRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.CreateOnePagerResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherListResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.UpdateStateReasonResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.service.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final QueryAvailableTeacherListService queryAvailableTeacherListService;
    private final QueryTeacherMyInfoService queryTeacherMyInfoService;
    private final CreateOnePagerFileFormService createOnePagerFileFormService;
    private final CreateOnePagerUrlFormService createOnePagerUrlFormService;
    private final UpdateOnePagerFileService updateOnePagerFileService;
    private final UpdateOnePagerUrlService updateOnePagerUrlService;
    private final DeleteOnePagerService deleteOnePagerService;
    private final UpdateOnePagerStateService updateOnePagerStateService;
    private final QuerySubmitOnePagerService querySubmitOnePagerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public QueryTeacherListResponse queryAvailableTeachers() {
        return queryAvailableTeacherListService.execute();
    }

    @GetMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    public QueryTeacherMyInfoResponse queryTeacherMyInfo() {
        return queryTeacherMyInfoService.execute();
    }

    @PostMapping("/onepager/forms-file")
    public ResponseEntity<CreateOnePagerResponse> createOnePagerFileForm(@ModelAttribute @Valid OnePagerFileFormRequest request) {
        Long id = createOnePagerFileFormService.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(CreateOnePagerResponse.of(id));
    }

    @PostMapping("/onepager/forms-url")
    public ResponseEntity<CreateOnePagerResponse> createOnePagerUrlForm(@RequestBody @Valid OnePagerUrlFormRequest request) {
        Long id = createOnePagerUrlFormService.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(CreateOnePagerResponse.of(id));
    }

    @PatchMapping("/onepager/forms-file/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOnePagerFileForm(
            @PathVariable("form-id") Long formId,
            @ModelAttribute @Valid OnePagerFileFormRequest request
    ) {
        updateOnePagerFileService.execute(request, formId);
    }

    @PatchMapping("/onepager/forms-url/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOnePagerUrlForm(
            @PathVariable("form-id") Long formId,
            @RequestBody @Valid OnePagerUrlFormRequest request
    ) {
        updateOnePagerUrlService.execute(request, formId);
    }

    @DeleteMapping("/onepager/forms/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOnePagerForm(@PathVariable("form-id") Long formId) {
        deleteOnePagerService.execute(formId);
    }

    @PatchMapping("/onepager/submissions/{submission-id}/status")
    @ResponseStatus(HttpStatus.OK)
    public UpdateStateReasonResponse updateOnePagerStatus(
        @RequestBody @Valid ChangeOnePagerStateRequest request,
        @PathVariable("submission-id") Long submissionId
    ) {
        return updateOnePagerStateService.execute(request, submissionId);
    }


    @GetMapping("/onepager/form/{form-id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryListSubmitOnePagerResponse queryListOnePager(
        @PathVariable("form-id") Long formId
    ) {
        return querySubmitOnePagerService.execute(formId);
    }
}
