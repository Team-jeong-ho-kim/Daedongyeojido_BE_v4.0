package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubLinkRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubMajorRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.validator.CreateClubValidator;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.facade.UserFacade;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateClubService {

    private final ClubRepository clubRepository;
    private final ClubMajorRepository clubMajorRepository;
    private final ClubLinkRepository clubLinkRepository;
    private final UserFacade userFacade;
    private final CreateClubValidator createClubValidator;

    @Transactional
    public void execute(ClubRequest request) {
        User clubApplicant = userFacade.getCurrentUser();

        createClubValidator.validate(request, clubApplicant);

        Club club = createClub(request, clubApplicant);
        List<ClubMajor> clubMajors = createClubMajor(request, club);
        List<ClubLink> clubLinks = createClubLink(request, club);

        clubRepository.save(club);
        clubMajorRepository.saveAll(clubMajors);
        clubLinkRepository.saveAll(clubLinks);
    }

    private Club createClub(ClubRequest request, User clubApplicant) {
        return Club.builder()
                .clubName(request.getClubName())
                .clubImage(request.getClubImage())
                .oneLiner(request.getOneLiner())
                .introduction(request.getIntroduction())
                .isOpen(false)
                .clubApplicant(clubApplicant)
                .build();
    }

    private List<ClubMajor> createClubMajor(ClubRequest request, Club cLub) {
        return request.getMajor().stream().map(major ->
                ClubMajor.builder()
                        .club(cLub)
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<ClubLink> createClubLink(ClubRequest request, Club club) {
        return Optional.ofNullable(request.getLink())
                .orElseGet(List::of)
                .stream()
                .map(link -> ClubLink.builder()
                        .club(club)
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
