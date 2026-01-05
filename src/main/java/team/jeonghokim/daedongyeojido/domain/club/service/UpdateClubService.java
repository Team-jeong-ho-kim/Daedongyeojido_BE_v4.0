package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.ClubRequest;
import team.jeonghokim.daedongyeojido.global.cache.CacheNames;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;

import static team.jeonghokim.daedongyeojido.global.cache.CacheNames.CLUB_DETAIL;

@Service
@RequiredArgsConstructor
public class UpdateClubService {

    private final ClubFacade clubFacade;
    private final S3Service s3Service;

    @CacheEvict(value = CLUB_DETAIL, key = "#clubId")
    @Transactional
    public void execute(Long clubId, ClubRequest request) {
        Club club = clubFacade.getClubById(clubId);
        String clubImage = s3Service.upload(request.getClubImage());

        club.updateClub(request, clubImage);
    }
}
