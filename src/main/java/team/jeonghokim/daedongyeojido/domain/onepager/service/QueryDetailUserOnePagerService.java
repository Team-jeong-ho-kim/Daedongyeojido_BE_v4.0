package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.OnePagerNotFoundException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.response.UserOnePagerDetailResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QueryDetailUserOnePagerService {
    private final OnePagerRepository onePagerRepository;

    public UserOnePagerDetailResponse execute(Long onePagerId) {
        OnePager onePager = onePagerRepository.findById(onePagerId)
            .orElseThrow(() -> OnePagerNotFoundException.EXCEPTION);

        String fileUrl = onePager.getFormFileUrl();
        String formUrl = onePager.getFormFile() == null ? onePager.getFormUrl() : null;

        LocalDateTime duration = null;
        if (onePager.getOnePagerDurationType() == OnePagerDurationType.DATE) {
            duration = onePager.getOnePagerDuration();
        }

        return UserOnePagerDetailResponse.of(
            onePager.getTitle(),
            onePager.getDescription(),
            duration,
            fileUrl,
            formUrl,
            onePager.getOnePagerDurationType()
        );
    }
}
