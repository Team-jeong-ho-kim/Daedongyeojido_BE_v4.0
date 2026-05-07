package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.request.SubmitOnePagerRequest;

@Service
@RequiredArgsConstructor
public class CreateSubmitOnePagerService {
    private final OnePagerRepository onePagerRepository;

    public void execute(SubmitOnePagerRequest request) {

    }
}
