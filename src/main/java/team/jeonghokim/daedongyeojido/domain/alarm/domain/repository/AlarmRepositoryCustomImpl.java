package team.jeonghokim.daedongyeojido.domain.alarm.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.AlarmResponse;
import team.jeonghokim.daedongyeojido.domain.alarm.presentation.dto.response.QAlarmResponse;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClub;
import team.jeonghokim.daedongyeojido.domain.club.domain.QClubAlarm;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUser;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUserAlarm;

import java.util.List;

@RequiredArgsConstructor
public class AlarmRepositoryCustomImpl implements AlarmRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QClubAlarm clubAlarm = QClubAlarm.clubAlarm;
    private final QUserAlarm userAlarm = QUserAlarm.userAlarm;

    @Override
    public List<AlarmResponse> findAllByClubId(Long clubId) {
        return queryFactory.select(new QAlarmResponse(
                clubAlarm.id,
                clubAlarm.title,
                clubAlarm.content
        ))
                .from(clubAlarm)
                .join(clubAlarm.club, QClub.club)
                .where(QClub.club.id.eq(clubId))
                .fetch();
    }

    @Override
    public List<AlarmResponse> findAllByUserId(Long userId) {
        return queryFactory.select(new QAlarmResponse(
                userAlarm.id,
                userAlarm.title,
                userAlarm.content
        ))
                .from(userAlarm)
                .join(userAlarm.receiver, QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetch();
    }
}
