package team.jeonghokim.daedongyeojido.domain.teacher.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static com.querydsl.jpa.JPAExpressions.selectOne;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.Teacher;
import team.jeonghokim.daedongyeojido.domain.teacher.domain.repository.vo.TeacherMatchInfo;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.club.domain.QClub.club;
import static team.jeonghokim.daedongyeojido.domain.teacher.domain.QTeacher.teacher;

@RequiredArgsConstructor
public class TeacherRepositoryCustomImpl implements TeacherRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Teacher> findAllAvailableTeachers() {
        return jpaQueryFactory
                .selectFrom(teacher)
                .orderBy(teacher.teacherName.asc())
                .fetch();
    }

    @Override
    public List<TeacherMatchInfo> findAllTeachersWithMatchedStatus() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                TeacherMatchInfo.class,
                                teacher.id,
                                teacher.teacherName,
                                selectOne()
                                        .from(club)
                                        .where(club.teacher.eq(teacher))
                                        .exists()
                        )
                )
                .from(teacher)
                .orderBy(teacher.teacherName.asc())
                .fetch();
    }
}
