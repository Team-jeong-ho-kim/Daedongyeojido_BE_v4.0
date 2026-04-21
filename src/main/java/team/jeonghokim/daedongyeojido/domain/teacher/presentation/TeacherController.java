package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerFileFormRequest;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.request.CreateOnePagerUrlFormRequest;
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
    private final CreateOnePagerFileFormService createOnePagerFormService;
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
    public void createOnePagerForm(@ModelAttribute CreateOnePagerFileFormRequest createOnePagerFormRequest) {
        createOnePagerFormService.execute(createOnePagerFormRequest);
    }

    @PostMapping("/onepager/forms-url")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOnePagerForm(@RequestBody CreateOnePagerUrlFormRequest createOnePagerUrlFormRequest) {
        createOnePagerUrlFormService.execute(createOnePagerUrlFormRequest);
    }
}
