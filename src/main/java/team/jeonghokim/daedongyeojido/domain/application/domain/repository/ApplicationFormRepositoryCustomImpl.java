package team.jeonghokim.daedongyeojido.domain.application.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.application.domain.QApplicationForm;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.ApplicationFormResponse;
import team.jeonghokim.daedongyeojido.domain.application.presentation.dto.response.QApplicationFormResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClub;

import java.util.List;

@RequiredArgsConstructor
public class ApplicationFormRepositoryCustomImpl implements ApplicationFormRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QApplicationForm applicationForm =  QApplicationForm.applicationForm;
    private final QClub club =  QClub.club;

    @Override
    public List<ApplicationFormResponse> findAllByClubId(Long clubId) {
        return queryFactory.select(new QApplicationFormResponse(
                        applicationForm.id,
                        applicationForm.applicationFormTitle,
                        club.clubName,
                        club.clubImage,
                        applicationForm.submissionDuration
                ))
                .from(applicationForm)
                .join(applicationForm.club, club)
                .where(club.id.eq(clubId))
                .fetch();
    }
}
