package team.jeonghokim.daedongyeojido.domain.teacher.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherListResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.presentation.dto.response.QueryTeacherMyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.teacher.service.QueryAvailableTeacherListService;
import team.jeonghokim.daedongyeojido.domain.teacher.service.QueryTeacherMyInfoService;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final QueryAvailableTeacherListService queryAvailableTeacherListService;
    private final QueryTeacherMyInfoService queryTeacherMyInfoService;

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
}
