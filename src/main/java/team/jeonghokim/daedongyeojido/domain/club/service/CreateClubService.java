package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplyClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyExistsClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class CreateClubService {

    private final ClubRepository clubRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(CreateClubRequest request) {
        User clubApplicant = userFacade.getCurrentUser();

        if (clubRepository.existsByClubApplicant(clubApplicant)) {
            throw AlreadyApplyClubException.EXCEPTION;
        }

        if (clubApplicant.getClub() != null) {
            throw AlreadyJoinClubException.EXCEPTION;
        }

        if (clubRepository.existsByClubName(request.getClubName())) {
            throw AlreadyExistsClubException.EXCEPTION;
        }

        clubRepository.save(createClub(request, clubApplicant));
    }

    private Club createClub(CreateClubRequest request, User clubApplicant) {
        return Club.builder()
                .clubName(request.getClubName())
                .clubImage(request.getClubImage())
                .oneLiner(request.getOneLiner())
                .introduction(request.getIntroduction())
                .major(request.getMajor())
                .link(request.getLink())
                .isOpened(false)
                .clubApplicant(clubApplicant)
                .build();
    }
}
