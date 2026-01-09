package team.jeonghokim.daedongyeojido.domain.resultduration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.resultduration.presentation.dto.response.ResultDurationResponse;

@Service
@RequiredArgsConstructor
public class QueryResultDurationService {
    private final ResultDurationRepository resultDurationRepository;

    @Transactional(readOnly = true)
    public ResultDurationResponse execute() {

        ResultDuration resultDuration = resultDurationRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> ResultDurationNotFoundException.EXCEPTION);

        return new ResultDurationResponse(resultDuration.getResultDurationTime());
    }
}
