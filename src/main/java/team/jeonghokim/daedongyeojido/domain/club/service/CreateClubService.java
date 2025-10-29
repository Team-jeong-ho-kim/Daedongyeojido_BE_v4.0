package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyApplyClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyExistsClubException;
import team.jeonghokim.daedongyeojido.domain.club.exception.AlreadyJoinClubException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.facade.UserFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        Club club = createClub(request, clubApplicant);

        List<ClubLink> clubLinks = new ArrayList<>();
        List<ClubMajor> clubMajors = createClubMajor(request, club);

        if (request.getLink() != null && !request.getLink().isEmpty()) {
            clubLinks = createClubLink(request, club);
        }

        club.getMajors().addAll(clubMajors);
        club.getLinks().addAll(clubLinks);

        clubRepository.save(club);
    }

    private Club createClub(CreateClubRequest request, User clubApplicant) {
        return Club.builder()
                .clubName(request.getClubName())
                .clubImage(request.getClubImage())
                .oneLiner(request.getOneLiner())
                .introduction(request.getIntroduction())
                .isOpened(false)
                .clubApplicant(clubApplicant)
                .build();
    }

    private List<ClubMajor> createClubMajor(CreateClubRequest request, Club club) {
        return request.getMajor().stream().map(major ->
                ClubMajor.builder()
                        .club(club)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<ClubLink> createClubLink(CreateClubRequest request, Club club) {
        return request.getLink().stream().map(link ->
                ClubLink.builder()
                        .club(club)
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
