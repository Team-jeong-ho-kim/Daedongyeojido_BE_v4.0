package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.SubmitOnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.SubmitOnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.QueryListSubmitOnePagerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuerySubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;
    private final SubmitOnePagerRepository submitOnePagerRepository;

    public QueryListSubmitOnePagerResponse execute(Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        List<SubmitOnePager> submitOnePagerList = submitOnePagerRepository
    }
}
