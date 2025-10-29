package team.jeonghokim.daedongyeojido.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseTimeIdEntity is a Querydsl query type for BaseTimeIdEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseTimeIdEntity extends EntityPathBase<BaseTimeIdEntity> {

    private static final long serialVersionUID = -969723545L;

    public static final QBaseTimeIdEntity baseTimeIdEntity = new QBaseTimeIdEntity("baseTimeIdEntity");

    public final QBaseIdEntity _super = new QBaseIdEntity(this);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QBaseTimeIdEntity(String variable) {
        super(BaseTimeIdEntity.class, forVariable(variable));
    }

    public QBaseTimeIdEntity(Path<? extends BaseTimeIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseTimeIdEntity(PathMetadata metadata) {
        super(BaseTimeIdEntity.class, metadata);
    }

}

