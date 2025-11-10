package team.jeonghokim.daedongyeojido.domain.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.UpdateMyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;
import team.jeonghokim.daedongyeojido.domain.user.service.InputMyInfoService;
import team.jeonghokim.daedongyeojido.domain.user.service.QueryMyInfoService;
import team.jeonghokim.daedongyeojido.domain.user.service.UpdateMyInfoService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final InputMyInfoService inputMyInfoService;
    private final QueryMyInfoService queryMyInfoService;
    private final UpdateMyInfoService updateMyInfoService;

    @PatchMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    public void inputMyInfo(@ModelAttribute @Valid MyInfoRequest request) {
        inputMyInfoService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MyInfoResponse queryMyInfo() {
        return queryMyInfoService.execute();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMyInfo(@ModelAttribute @Valid UpdateMyInfoRequest request) {
        updateMyInfoService.execute(request);
    }
}
