package team.jeonghokim.daedongyeojido.domain.onepager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.OnePager;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.enums.OnePagerDurationType;
import team.jeonghokim.daedongyeojido.domain.onepager.domain.repository.OnePagerRepository;
import team.jeonghokim.daedongyeojido.domain.onepager.exception.InvalidRoleException;
import team.jeonghokim.daedongyeojido.domain.onepager.presentation.dto.OnePagerListResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOnePagerListService {

    private final OnePagerRepository onePagerRepository;
    private final UserFacade userFacade;

    @Transactional
    public List<OnePagerListResponse> execute() {

        User user = userFacade.getCurrentUser();
        Role userRole = user.getRole();

        if (userRole != Role.TEACHER && userRole != Role.CLUB_LEADER && userRole != Role.CLUB_MEMBER) {
            throw InvalidRoleException.EXCEPTION;
        }

        LocalDateTime now = LocalDateTime.now();
        List<OnePager> onePagers = onePagerRepository.findByOnePagerDurationTypeOrOnePagerDurationAfter(OnePagerDurationType.INFINITY, now);

        return onePagers.stream().map(onePager -> new OnePagerListResponse(
                onePager.getId(),
                onePager.getTitle(),
                onePager.getTeacherName(),
                onePager.getOnePagerDurationType(),
                onePager.getOnePagerDuration()
        )).toList();
    }
}
