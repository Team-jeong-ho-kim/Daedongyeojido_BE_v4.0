package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public void createOnePagerFileForm(@ModelAttribute @Valid OnePagerFileFormRequest createOnePagerFormRequest) {
        createOnePagerFileFormService.execute(createOnePagerFormRequest);
    }

    @PostMapping("/onepager/forms-url")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOnePagerUrlForm(@RequestBody @Valid OnePagerUrlFormRequest createOnePagerUrlFormRequest) {
        createOnePagerUrlFormService.execute(createOnePagerUrlFormRequest);
    }

    @PatchMapping("/onepager/forms-file/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOnePagerFileForm(
            @PathVariable("form-id") Long formId,
            @RequestBody @Valid OnePagerFileFormRequest request                           ) {
        updateOnePagerFileService.execute(request, formId);
    }

    @PatchMapping("/onepager/forms-url/{form-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOnePagerUrlForm(
            @PathVariable("form-id") Long formId,
            @RequestBody @Valid OnePagerUrlFormRequest request                         ) {
        updateOnePagerUrlService.execute(request, formId);
    }
}
