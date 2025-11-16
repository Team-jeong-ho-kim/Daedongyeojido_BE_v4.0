package team.jeonghokim.daedongyeojido.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.application.domain.repository.ApplicationFormRepository;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllApplicationFormService {
    private final ApplicationFormRepository applicationFormRepository;

    @Transactional(readOnly = true)
    public List<ApplicationFormListResponse> execute() {

        return applicationFormRepository.findAll()
                .stream()
                .map(ApplicationFormListResponse::of)
                .toList();
    }
}
