package team.jeonghokim.daedongyeojido.domain.club.service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplyClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyExistsClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;

@Component
@RequiredArgsConstructor
public class CreateClubValidator {

    private final ClubRepository clubRepository;

    public void validate(ClubRequest request, User clubApplicant) {
        if (clubRepository.existsByClubApplicant(clubApplicant)) {
            throw AlreadyApplyClubException.EXCEPTION;
        }

        if (clubApplicant.getClub() != null) {
            throw AlreadyJoinClubException.EXCEPTION;
        }

        if (clubRepository.existsByClubName(request.getClubName())) {
            throw AlreadyExistsClubException.EXCEPTION;
        }
    }
}
