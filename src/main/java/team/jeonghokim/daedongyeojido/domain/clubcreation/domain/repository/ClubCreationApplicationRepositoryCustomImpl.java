package team.jeonghokim.daedongyeojido.domain.clubcreation.domain.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.clubcreation.domain.enums.ClubCreationApplicationStatus;
import team.jeonghokim.daedongyeojido.domain.clubcreation.presentation.dto.response.ClubCreationApplicationSummaryResponse;
import team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static team.jeonghokim.daedongyeojido.domain.clubcreation.domain.QClubCreationApplication.clubCreationApplication;
import static team.jeonghokim.daedongyeojido.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class ClubCreationApplicationRepositoryCustomImpl implements ClubCreationApplicationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ClubCreationApplicationSummaryResponse> findAllSummary() {
        return findSummary(null);
    }

    @Override
    public List<ClubCreationApplicationSummaryResponse> findAllSummaryByTeacherId(Long teacherId) {
        return findSummary(clubCreationApplication.teacher.id.eq(teacherId));
    }

    private List<ClubCreationApplicationSummaryResponse> findSummary(Predicate predicate) {
        EnumPath<Major> major = Expressions.enumPath(Major.class, "major");

        return jpaQueryFactory
                .from(clubCreationApplication)
                .join(clubCreationApplication.applicant, user)
                .leftJoin(clubCreationApplication.majors, major)
                .where(
                        predicate,
                        clubCreationApplication.status.in(
                                ClubCreationApplicationStatus.SUBMITTED,
                                ClubCreationApplicationStatus.UNDER_REVIEW,
                                ClubCreationApplicationStatus.CHANGES_REQUESTED
                        )
                )
                .orderBy(clubCreationApplication.id.desc())
                .transform(groupBy(clubCreationApplication.id).list(
                        com.querydsl.core.types.Projections.constructor(
                                ClubCreationApplicationSummaryResponse.class,
                                clubCreationApplication.id,
                                clubCreationApplication.clubName,
                                clubCreationApplication.clubImage,
                                clubCreationApplication.introduction,
                                clubCreationApplication.status,
                                clubCreationApplication.revision,
                                GroupBy.list(major),
                                user.userName,
                                clubCreationApplication.lastSubmittedAt
                        )
                ));
    }
}
