package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubApplication;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubApplicationRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.validator.CreateClubValidator;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateClubService {

    private final ClubRepository clubRepository;
    private final UserFacade userFacade;
    private final CreateClubValidator createClubValidator;
    private final ClubApplicationRepository clubApplicationRepository;
    private final S3Service s3Service;

    @Transactional
    public void execute(ClubRequest request) {
        User clubApplicant = userFacade.getCurrentUser();

        createClubValidator.validate(request, clubApplicant);

        List<ClubMajor> clubMajors = createClubMajor(request);
        List<ClubLink> clubLinks = createClubLink(request);

        Club club = createClub(request, clubApplicant, clubMajors, clubLinks);

        clubRepository.save(club);

        clubApplicationRepository.save(ClubApplication.builder()
                .club(club)
                .clubLeader(clubApplicant)
                .isApproved(false)
                .build());
    }

    private Club createClub(ClubRequest request, User clubApplicant, List<ClubMajor> clubMajors, List<ClubLink> clubLinks) {
        return Club.builder()
                .clubName(request.getClubName())
                .clubImage(s3Service.upload(request.getClubImage()))
                .oneLiner(request.getOneLiner())
                .introduction(request.getIntroduction())
                .isOpen(false)
                .clubApplicant(clubApplicant)
                .clubMajors(clubMajors)
                .clubLinks(clubLinks)
                .build();
    }

    private List<ClubMajor> createClubMajor(ClubRequest request) {
        return request.getMajor().stream().map(major ->
                ClubMajor.builder()
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<ClubLink> createClubLink(ClubRequest request) {
        return Optional.ofNullable(request.getLink())
                .orElseGet(List::of)
                .stream()
                .map(link -> ClubLink.builder()
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }
}
