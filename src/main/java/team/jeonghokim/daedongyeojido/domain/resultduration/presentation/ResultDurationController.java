package team.jeonghokim.daedongyeojido.domain.resultduration.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.jeonghokim.daedongyeojido.domain.resultduration.presentation.dto.response.ResultDurationResponse;
import team.jeonghokim.daedongyeojido.domain.resultduration.service.QueryResultDurationService;

@RestController
@RequestMapping("/result-duration")
@RequiredArgsConstructor
public class ResultDurationController {
    private final QueryResultDurationService queryResultDurationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultDurationResponse queryResultDuration() {
        return queryResultDurationService.execute();
    }
}
