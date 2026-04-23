package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.OnePagerUrlFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherListResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.service.CreateOnePagerFileFormService;
import team.jeonghokim.daedongyeojido.domain.teacher.service.CreateOnePagerUrlFormService;
import team.jeonghokim.daedongyeojido.domain.teacher.service.QueryAvailableTeacherListService;
import team.jeonghokim.daedongyeojido.domain.teacher.service.QueryTeacherMyInfoService;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final QueryAvailableTeacherListService queryAvailableTeacherListService;
    private final QueryTeacherMyInfoService queryTeacherMyInfoService;
    private final CreateOnePagerFileFormService createOnePagerFileFormService;
    private final CreateOnePagerUrlFormService createOnePagerUrlFormService;

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
}
