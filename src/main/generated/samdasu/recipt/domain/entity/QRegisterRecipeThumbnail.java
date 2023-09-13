package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegisterRecipeThumbnail is a Querydsl query type for RegisterRecipeThumbnail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegisterRecipeThumbnail extends EntityPathBase<RegisterRecipeThumbnail> {

    private static final long serialVersionUID = -48572551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegisterRecipeThumbnail registerRecipeThumbnail = new QRegisterRecipeThumbnail("registerRecipeThumbnail");

    public final samdasu.recipt.domain.common.QBaseTimeEntity _super = new samdasu.recipt.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath filename = createString("filename");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QRegisterRecipe registerRecipe;

    public final ArrayPath<byte[], Byte> thumbnailData = createArray("thumbnailData", byte[].class);

    public final NumberPath<Long> thumbnailId = createNumber("thumbnailId", Long.class);

    public final StringPath type = createString("type");

    public QRegisterRecipeThumbnail(String variable) {
        this(RegisterRecipeThumbnail.class, forVariable(variable), INITS);
    }

    public QRegisterRecipeThumbnail(Path<? extends RegisterRecipeThumbnail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegisterRecipeThumbnail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegisterRecipeThumbnail(PathMetadata metadata, PathInits inits) {
        this(RegisterRecipeThumbnail.class, metadata, inits);
    }

    public QRegisterRecipeThumbnail(Class<? extends RegisterRecipeThumbnail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.registerRecipe = inits.isInitialized("registerRecipe") ? new QRegisterRecipe(forProperty("registerRecipe"), inits.get("registerRecipe")) : null;
    }

}

