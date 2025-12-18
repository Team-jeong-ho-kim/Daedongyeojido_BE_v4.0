package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.request.DecideResultDurationRequest;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.ResultDuration;
import team.jeonghokim.daedongyeojido.domain.resultduration.domain.repository.ResultDurationRepository;
import team.jeonghokim.daedongyeojido.domain.resultduration.exception.ResultDurationAlreadySetException;

@Service
@RequiredArgsConstructor
public class DecideResultDurationService {

    private final ResultDurationRepository resultDurationRepository;

    @Transactional
    public void execute(DecideResultDurationRequest request) {

        if (resultDurationRepository.findTopByOrderByIdDesc().isPresent()) {
            throw ResultDurationAlreadySetException.EXCEPTION;
        }

        resultDurationRepository.save(new ResultDuration(request.resultDuration()));
    }
}
