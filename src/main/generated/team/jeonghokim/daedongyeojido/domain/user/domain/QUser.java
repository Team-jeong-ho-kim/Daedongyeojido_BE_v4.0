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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final team.jeonghokim.daedongyeojido.global.entity.QBaseIdEntity _super = new team.jeonghokim.daedongyeojido.global.entity.QBaseIdEntity(this);

    public final StringPath accountId = createString("accountId");

    public final team.jeonghokim.daedongyeojido.domain.club.domain.QClub club;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath introduction = createString("introduction");

    public final ListPath<UserLink, QUserLink> links = this.<UserLink, QUserLink>createList("links", UserLink.class, QUserLink.class, PathInits.DIRECT2);

    public final ListPath<UserMajor, QUserMajor> majors = this.<UserMajor, QUserMajor>createList("majors", UserMajor.class, QUserMajor.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role> role = createEnum("role", team.jeonghokim.daedongyeojido.domain.user.domain.enums.Role.class);

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new team.jeonghokim.daedongyeojido.domain.club.domain.QClub(forProperty("club"), inits.get("club")) : null;
    }

}

