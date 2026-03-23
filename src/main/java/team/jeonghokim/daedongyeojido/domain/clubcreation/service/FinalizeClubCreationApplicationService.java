package team.jeonghokim.daedongyeojido.domain.clubcreation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.enums.AlarmType;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.enums.ClubStatus;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.ClubCreationApplication;
import team.jeonghokim.daedongyeojido.domain.teacher.exception.TeacherAlreadyMatchedException;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.infrastructure.event.alarm.factory.AlarmEventFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalizeClubCreationApplicationService {

    private final ClubRepository clubRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AlarmEventFactory alarmEventFactory;

    @Transactional
    public void execute(ClubCreationApplication application) {
        if (application.getApplicant().getClub() != null) {
            return;
        }

        if (clubRepository.existsByTeacher(application.getTeacher())) {
            throw TeacherAlreadyMatchedException.EXCEPTION;
        }

        Club club = Club.builder()
                .clubName(application.getClubName())
                .clubImage(application.getClubImage())
                .clubCreationForm(application.getClubCreationForm())
                .oneLiner(application.getOneLiner())
                .introduction(application.getIntroduction())
                .clubStatus(ClubStatus.OPEN)
                .clubApplicant(application.getApplicant())
                .clubMajors(toClubMajors(application))
                .clubLinks(toClubLinks(application))
                .teacher(application.getTeacher())
                .build();

        clubRepository.save(club);

        User applicant = application.getApplicant();
        applicant.approvedClub(club);
        application.approve();

        eventPublisher.publishEvent(
                alarmEventFactory.createUserAlarmEvent(applicant, club, AlarmType.CLUB_CREATION_ACCEPTED)
        );
    }

    private List<ClubMajor> toClubMajors(ClubCreationApplication application) {
        return application.getMajors().stream()
                .map(major -> ClubMajor.builder()
                        .major(major)
                        .build())
                .toList();
    }

    private List<ClubLink> toClubLinks(ClubCreationApplication application) {
        return application.getLinks().stream()
                .map(link -> ClubLink.builder()
                        .link(link)
                        .build())
                .toList();
    }
}
