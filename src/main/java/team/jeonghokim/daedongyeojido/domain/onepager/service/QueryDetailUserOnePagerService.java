package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.UserOnePagerDetailResponse;

@Service
@RequiredArgsConstructor
public class QueryDetailUserOnePagerService {
    private final OnePagerRepository onePagerRepository;

    public UserOnePagerDetailResponse execute(Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        String fileUrl = onePager.getFormFile() != null ? onePager.getFormFile().getFileUrl() : onePager.getFormUrl();

        return UserOnePagerDetailResponse.from(
            onePager.getTitle(),
            onePager.getDescription(),
            onePager.getOnePagerDuration(),
            fileUrl
        );
    }
}
