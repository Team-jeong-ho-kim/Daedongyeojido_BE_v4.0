package team.jeonghokim.daedongyeojido.domain.onepager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QueryOnePagerListService {
    private static final Set<Role> ALLOWED_ROLES = Set.of(Role.TEACHER, Role.CLUB_LEADER, Role.CLUB_MEMBER);

    private final OnePagerRepository onePagerRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<OnePagerListResponse> execute() {
        User user = userFacade.getCurrentUser();
        if (!ALLOWED_ROLES.contains(user.getRole())) {
            throw InvalidRoleException.EXCEPTION;
        }

        List<OnePager> onePagers = onePagerRepository.findByOnePagerDurationTypeOrOnePagerDurationAfter(OnePagerDurationType.INFINITY, LocalDateTime.now());
        return onePagers.stream().map(onePager -> new OnePagerListResponse(
                onePager.getId(),
                onePager.getTitle(),
                onePager.getTeacherName(),
                onePager.getOnePagerDurationType(),
                onePager.getOnePagerDuration()
        )).toList();
    }
}
