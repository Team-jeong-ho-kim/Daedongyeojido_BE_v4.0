package team.jeonghokim.daedongyeojido.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseIdEntity is a Querydsl query type for BaseIdEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseIdEntity extends EntityPathBase<BaseIdEntity> {

    private static final long serialVersionUID = -949664390L;

    public static final QBaseIdEntity baseIdEntity = new QBaseIdEntity("baseIdEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBaseIdEntity(String variable) {
        super(BaseIdEntity.class, forVariable(variable));
    }

    public QBaseIdEntity(Path<? extends BaseIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseIdEntity(PathMetadata metadata) {
        super(BaseIdEntity.class, metadata);
    }

}

