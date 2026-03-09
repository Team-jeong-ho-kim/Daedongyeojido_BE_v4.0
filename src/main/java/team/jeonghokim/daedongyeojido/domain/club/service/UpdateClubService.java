package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.facade.ClubFacade;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.UpdateClubRequest;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

@Service
@RequiredArgsConstructor
public class UpdateClubService {

    private final ClubFacade clubFacade;
    private final S3Service s3Service;

    @Transactional
    public void execute(Long clubId, UpdateClubRequest request) {
        Club club = clubFacade.getClubById(clubId);
        String clubImage = s3Service.upload(request.clubImage(), FileType.IMAGE);

        club.updateClub(request, clubImage);
    }
}
