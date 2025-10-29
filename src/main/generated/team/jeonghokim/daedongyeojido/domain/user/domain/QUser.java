package team.jeonghokim.daedongyeojido.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1994891311L;

    public static final QUser user = new QUser("user");

    public final team.jeonghokim.daedongyeojido.global.entity.QBaseIdEntity _super = new team.jeonghokim.daedongyeojido.global.entity.QBaseIdEntity(this);

    public final StringPath accountId = createString("accountId");

    public final StringPath classNumber = createString("classNumber");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath introduction = createString("introduction");

    public final ListPath<String, StringPath> links = this.<String, StringPath>createList("links", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major, EnumPath<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major>> majors = this.<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major, EnumPath<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major>>createList("majors", team.jeonghokim.daedongyeojido.domain.user.domain.enums.Major.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role> role = createEnum("role", team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role.class);

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

