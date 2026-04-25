package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherListResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createOnePagerFileForm(@ModelAttribute @Valid OnePagerFileFormRequest request) {
        createOnePagerFileFormService.execute(request);
    }

    @PostMapping("/onepager/forms-url")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOnePagerUrlForm(@RequestBody @Valid OnePagerUrlFormRequest request) {
        createOnePagerUrlFormService.execute(request);
    }

    @PatchMapping("/onepager/forms-file/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOnePagerFileForm(
            @PathVariable("form-id") Long formId,
            @RequestBody @Valid OnePagerFileFormRequest request
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
}
