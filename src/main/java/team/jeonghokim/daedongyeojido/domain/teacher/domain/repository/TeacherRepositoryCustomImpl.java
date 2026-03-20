package team.jeonghokim.daedongyeojido.domain.teacher.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
                .leftJoin(club).on(club.teacher.eq(teacher))
                .where(club.id.isNull())
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
                                club.id.isNotNull()
                        )
                )
                .from(teacher)
                .leftJoin(club).on(club.teacher.eq(teacher))
                .orderBy(teacher.teacherName.asc())
                .fetch();
    }
}
