package team.jeonghokim.daedongyeojido.domain.onepager.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.onepager.service.QueryOnePagerListService;

import java.util.List;

@RestController
@RequestMapping("/onepager")
@RequiredArgsConstructor
public class OnePagerController {

    private final QueryOnePagerListService queryOnePagerListService;

    @GetMapping("/forms")
    @ResponseStatus(HttpStatus.OK)
    public List<OnePagerListResponse> queryOnePagerList() {
        return queryOnePagerListService.execute();
    }
}
