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
import team.jeonghokim.daedongyeojido.domain.club.exception.ClubNotFoundException;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateClubService {

    private final ClubRepository clubRepository;
    private final ClubMajorRepository clubMajorRepository;
    private final ClubLinkRepository clubLinkRepository;

    @Transactional
    public void execute(Long clubId, ClubRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        club.updateClub(request);
        updateClubMajor(club, request);
        updateClubLink(club, request);
    }

    private void updateClubMajor(Club club, ClubRequest request) {
        clubMajorRepository.deleteAllByClub(club);
        List<ClubMajor> clubMajors = request.getMajor().stream()
                .map(major -> new ClubMajor(club, major))
                .toList();

        clubMajorRepository.saveAll(clubMajors);
    }

    private void updateClubLink(Club club, ClubRequest request) {
        if (request.getLink() != null) {
            clubLinkRepository.deleteAllByClub(club);
            List<ClubLink> clubLinks = request.getLink().stream()
                    .map(link -> new ClubLink(club, link))
                    .toList();

            clubLinkRepository.saveAll(clubLinks);
        }
    }
}
