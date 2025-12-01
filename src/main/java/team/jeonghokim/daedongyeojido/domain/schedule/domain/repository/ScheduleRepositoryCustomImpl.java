package team.jeonghokim.daedongyeojido.domain.schedule.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.InterviewScheduleResponse;
import team.jeonghokim.daedongyeojido.domain.schedule.presentation.dto.response.QInterviewScheduleResponse;

import java.util.List;

import static team.jeonghokim.daedongyeojido.domain.schedule.domain.QSchedule.schedule;
import static team.jeonghokim.daedongyeojido.domain.submission.domain.QSubmission.submission;
import static team.jeonghokim.daedongyeojido.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InterviewScheduleResponse> findAllSchedulesByClubId(Long clubId) {
        return jpaQueryFactory
                .select(new QInterviewScheduleResponse(
                        schedule.id,
                        user.userName,
                        user.classNumber,
                        submission.major,
                        schedule.interviewSchedule
                ))
                .from(schedule)
                .join(schedule.applicant, user)
                .join(submission)
                .on(
                    submission.user.id.eq(user.id),
                    submission.applicationForm.club.id.eq(clubId)
                )
                .orderBy(schedule.interviewSchedule.asc())
                .fetch();
    }
}
