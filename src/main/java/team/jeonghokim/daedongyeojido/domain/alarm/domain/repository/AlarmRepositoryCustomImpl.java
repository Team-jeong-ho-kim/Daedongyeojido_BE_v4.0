package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.alarm.domain.QAlarm;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClub;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUser;

import java.util.List;

@RequiredArgsConstructor
public class AlarmRepositoryCustomImpl implements AlarmRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QAlarm alarm = QAlarm.alarm;

    @Override
    public List<AlarmResponse> findAllByClubId(Long clubId) {
        return queryFactory.select(new QAlarmResponse(
                alarm.id,
                alarm.title,
                alarm.content
        ))
                .from(alarm)
                .join(alarm.club, QClub.club)
                .where(QClub.club.id.eq(clubId))
                .fetch();
    }

    @Override
    public List<AlarmResponse> findAllByUserId(Long userId) {
        return queryFactory.select(new QAlarmResponse(
                alarm.id,
                alarm.title,
                alarm.content
        ))
                .from(alarm)
                .join(alarm.receiver, QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetch();
    }
}
