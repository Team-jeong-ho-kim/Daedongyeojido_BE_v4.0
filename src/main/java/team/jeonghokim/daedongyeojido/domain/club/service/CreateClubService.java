package team.jeonghokim.daedongyeojido.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.AdminAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.repository.AdminAlarmRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.club.presentation.dto.request.CreateClubRequest;
import team.jeonghokim.daedongyeojido.domain.club.service.validator.CreateClubValidator;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.facade.UserFacade;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;
import team.jeonghokim.daedongyeojido.infrastructure.s3.service.S3Service;
import team.jeonghokim.daedongyeojido.infrastructure.s3.type.FileType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateClubService {

    private final ClubRepository clubRepository;
    private final UserFacade userFacade;
    private final CreateClubValidator createClubValidator;
    private final S3Service s3Service;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;
    private final AdminAlarmRepository adminAlarmRepository;

    @Transactional
    public void execute(CreateClubRequest request) {
        User clubApplicant = userFacade.getCurrentUser();

        createClubValidator.validate(request, clubApplicant);

        List<ClubMajor> clubMajors = createClubMajor(request);
        List<ClubLink> clubLinks = createClubLink(request);

        Club club = createClub(request, clubApplicant, clubMajors, clubLinks);

        createUserAlarm(club, clubApplicant);
        createAdminAlarm(club);

        clubRepository.save(club);
    }

    private Club createClub(CreateClubRequest request, User clubApplicant, List<ClubMajor> clubMajors, List<ClubLink> clubLinks) {

        return Club.builder()
                .clubName(request.clubName())
                .clubImage(s3Service.upload(request.clubImage(), FileType.IMAGE))
                .clubCreationForm(s3Service.upload(request.clubCreationForm(), FileType.DOCUMENT))
                .oneLiner(request.oneLiner())
                .introduction(request.introduction())
                .isOpen(false)
                .clubApplicant(clubApplicant)
                .clubMajors(clubMajors)
                .clubLinks(clubLinks)
                .build();
    }

    private List<ClubMajor> createClubMajor(CreateClubRequest request) {

        return request.major().stream().map(major ->
                ClubMajor.builder()
                        .major(major)
                        .build())
                .collect(Collectors.toList());
    }

    private List<ClubLink> createClubLink(CreateClubRequest request) {

        return Optional.ofNullable(request.link())
                .orElseGet(List::of)
                .stream()
                .map(link -> ClubLink.builder()
                        .link(link)
                        .build())
                .collect(Collectors.toList());
    }

    private void createUserAlarm(Club club, User clubApplicant) {

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(clubApplicant, club, AlarmType.CREATE_CLUB_APPLY)
        );
    }

    public void createAdminAlarm(Club club) {

        AdminAlarm adminAlarm = AdminAlarm.builder()
                    .title(AlarmType.REQUEST_CLUB_CREATION.formatTitle(club.getClubName()))
                    .content(AlarmType.REQUEST_CLUB_CREATION.formatContent(club.getClubName()))
                    .alarmType(AlarmType.REQUEST_CLUB_CREATION)
                .build();

        adminAlarmRepository.save(adminAlarm);
    }
}
