package team.jeonghokim.daedongyeojido.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.admin.presentation.dto.response.TemporarySeedDevelopDataResponse;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.Announcement;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.AnnouncementMajor;
import team.jeonghokim.daedongyeojido.domain.announcement.domain.repository.AnnouncementRepository;
import team.jeonghokim.daedongyeojido.domain.club.domain.Club;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubLink;
import team.jeonghokim.daedongyeojido.domain.club.domain.ClubMajor;
import team.jeonghokim.daedongyeojido.domain.club.domain.enums.ClubStatus;
import team.jeonghokim.daedongyeojido.domain.club.domain.repository.ClubRepository;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.TeacherRepository;
import team.jeonghokim.daedongyeojido.domain.user.domain.User;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role;
import team.jeonghokim.daedongyeojido.domain.user.domain.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemporarySeedDevelopDataService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final ClubRepository clubRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TemporarySeedDevelopDataResponse execute() {
        Teacher teacher = getOrCreateTeacher();

        SeedClubSpec alpha = new SeedClubSpec(
                "백엔드연구회",
                "Spring Boot와 데이터 설계를 함께 다루는 백엔드 중심 동아리",
                "API와 서버를 만드는 동아리입니다.",
                "https://example.com/backend",
                "seed_club_leader_01",
                "S001",
                "2101",
                Major.BE,
                "백엔드에 흥미가 있는 학생",
                "간단한 REST API 설계 과제"
        );

        SeedClubSpec design = new SeedClubSpec(
                "디자인스튜디오",
                "브랜딩과 UI를 함께 만드는 디자인 동아리",
                "프로덕트 디자인과 브랜딩을 실습하는 동아리입니다.",
                "https://example.com/design",
                "seed_club_leader_02",
                "S002",
                "2102",
                Major.DESIGN,
                "시각적으로 표현하는 걸 좋아하는 학생",
                "간단한 포스터 리디자인 과제"
        );

        SeedClubSpec ai = new SeedClubSpec(
                "AI랩",
                "모델 실험과 데이터 분석을 해보는 AI 동아리",
                "데이터와 모델링을 가볍게 시작해보는 동아리입니다.",
                "https://example.com/ai",
                "seed_club_leader_03",
                "S003",
                "2103",
                Major.AI,
                "AI 실험과 분석에 관심 있는 학생",
                "짧은 데이터 해석 과제"
        );

        List<String> clubNames = new ArrayList<>();
        List<String> announcementTitles = new ArrayList<>();

        for (SeedClubSpec spec : List.of(alpha, design, ai)) {
            Club club = getOrCreateClub(spec, teacher);
            clubNames.add(club.getClubName());

            Announcement announcement = getOrCreateAnnouncement(spec, club);
            announcementTitles.add(announcement.getTitle());
        }

        return new TemporarySeedDevelopDataResponse(clubNames, announcementTitles);
    }

    private Teacher getOrCreateTeacher() {
        return teacherRepository.findByAccountId("seed-teacher-01")
                .orElseGet(() -> teacherRepository.save(
                        Teacher.builder()
                                .accountId("seed-teacher-01")
                                .teacherName("시드교사")
                                .password(passwordEncoder.encode("seed1234"))
                                .build()
                ));
    }

    private Club getOrCreateClub(SeedClubSpec spec, Teacher teacher) {
        Optional<Club> existing = clubRepository.findAll().stream()
                .filter(club -> club.getClubName().equals(spec.clubName()))
                .findFirst();

        if (existing.isPresent()) {
            return existing.get();
        }

        User leader = userRepository.findByAccountId(spec.accountId())
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .accountId(spec.accountId())
                                .password(passwordEncoder.encode("seed1234"))
                                .userName(spec.userName())
                                .classNumber(spec.classNumber())
                                .role(Role.STUDENT)
                                .build()
                ));

        Club club = clubRepository.save(
                Club.builder()
                        .clubName(spec.clubName())
                        .clubImage("https://dsm-s3-bucket-entry.s3.ap-northeast-2.amazonaws.com/seed-club-image.png")
                        .clubCreationForm("https://dsm-s3-bucket-entry.s3.ap-northeast-2.amazonaws.com/seed-club-form.pdf")
                        .oneLiner(spec.oneLiner())
                        .introduction(spec.introduction())
                        .clubStatus(ClubStatus.OPEN)
                        .clubApplicant(leader)
                        .clubMajors(List.of(
                                ClubMajor.builder().major(spec.major()).build()
                        ))
                        .clubLinks(List.of(
                                ClubLink.builder().link(spec.link()).build()
                        ))
                        .teacher(teacher)
                        .build()
        );

        leader.approvedClub(club);
        userRepository.save(leader);

        return club;
    }

    private Announcement getOrCreateAnnouncement(SeedClubSpec spec, Club club) {
        return announcementRepository.findAll().stream()
                .filter(announcement -> announcement.getTitle().equals(spec.clubName() + " 모집 공고"))
                .findFirst()
                .orElseGet(() -> announcementRepository.save(
                        Announcement.builder()
                                .title(spec.clubName() + " 모집 공고")
                                .deadline(LocalDate.now().plusDays(14))
                                .introduction(spec.oneLiner())
                                .talentDescription(spec.talentDescription())
                                .assignment(spec.assignment())
                                .phoneNumber("01012341234")
                                .club(club)
                                .announcementMajors(List.of(
                                        AnnouncementMajor.builder().major(spec.major()).build()
                                ))
                                .build()
                ));
    }

    private record SeedClubSpec(
            String clubName,
            String oneLiner,
            String introduction,
            String link,
            String accountId,
            String userName,
            String classNumber,
            Major major,
            String talentDescription,
            String assignment
    ) {
    }
}
