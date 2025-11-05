package team.jeonghokim.daedongyeojido.domain.user.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUser;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUserLink;
import team.jeonghokim.daedongyeojido.domain.user.domain.QUserMajor;
import team.jeonghokim.daedongyeojido.domain.user.presentation.dto.response.MyInfoResponse;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QUserMajor userMajor = QUserMajor.userMajor;
    private final QUserLink userLink = QUserLink.userLink;

    @Override
    public Optional<MyInfoResponse> findByUserId(Long userId) {
        MyInfoResponse myInfoResponse = queryFactory.select(Projections.constructor(MyInfoResponse.class,
                        user.userName,
                        user.classNumber,
                        user.phoneNumber,
                        user.introduction,
                        user.club,
                        userMajor.major,
                        userLink.link,
                        user.profileImage))
                .from(user)
                .leftJoin(userMajor).on(userMajor.user.eq(user))
                .leftJoin(userLink).on(userLink.user.eq(user))
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(myInfoResponse);
    }
}
