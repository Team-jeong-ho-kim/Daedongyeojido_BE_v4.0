package team.jeonghokim.daedongyeojido.domain.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.request.MyInfoRequest;
import team.jeonghokim.daedongyeojido.domain.user.service.InputMyInfoService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final InputMyInfoService inputMyInfoService;

    @PatchMapping("/my-info")
    @ResponseStatus(HttpStatus.OK)
    public void inputMyInfo(@RequestPart(name = "request") @Valid MyInfoRequest request,
                            @RequestPart(name = "file") MultipartFile file) {
        inputMyInfoService.execute(request, file);
    }
}
