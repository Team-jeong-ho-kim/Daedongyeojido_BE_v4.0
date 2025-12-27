package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationNotFoundException;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.ResultDurationRequest;

@Service
@RequiredArgsConstructor
public class UpdateResultDurationService {
    private final ResultDurationRepository resultDurationRepository;

    @Transactional
    public void execute(Long resultDurationId, ResultDurationRequest request) {

        ResultDuration resultDuration = resultDurationRepository.findById(resultDurationId)
                .orElseThrow(() -> ResultDurationNotFoundException.EXCEPTION);

        resultDuration.update(request.resultDuration());
    }
}
