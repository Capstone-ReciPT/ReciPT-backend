package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGpt is a Querydsl query type for Gpt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGpt extends EntityPathBase<Gpt> {

    private static final long serialVersionUID = 1508727113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGpt gpt = new QGpt("gpt");

    public final samdasu.recipt.domain.common.QBaseTimeEntity _super = new samdasu.recipt.domain.common.QBaseTimeEntity(this);

    public final StringPath context = createString("context");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Long> gptId = createNumber("gptId", Long.class);

    public final StringPath ingredient = createString("ingredient");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<RegisterRecipe, QRegisterRecipe> registerRecipes = this.<RegisterRecipe, QRegisterRecipe>createList("registerRecipes", RegisterRecipe.class, QRegisterRecipe.class, PathInits.DIRECT2);

    public final QUser user;

    public QGpt(String variable) {
        this(Gpt.class, forVariable(variable), INITS);
    }

    public QGpt(Path<? extends Gpt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGpt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGpt(PathMetadata metadata, PathInits inits) {
        this(Gpt.class, metadata, inits);
    }

    public QGpt(Class<? extends Gpt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

