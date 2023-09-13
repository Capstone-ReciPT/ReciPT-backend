package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImageFile is a Querydsl query type for ImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageFile extends EntityPathBase<ImageFile> {

    private static final long serialVersionUID = 1303398293L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImageFile imageFile = new QImageFile("imageFile");

    public final samdasu.recipt.domain.common.QBaseTimeEntity _super = new samdasu.recipt.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath filename = createString("filename");

    public final ArrayPath<byte[], Byte> imageData = createArray("imageData", byte[].class);

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QRegisterRecipe registerRecipe;

    public final StringPath type = createString("type");

    public QImageFile(String variable) {
        this(ImageFile.class, forVariable(variable), INITS);
    }

    public QImageFile(Path<? extends ImageFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImageFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImageFile(PathMetadata metadata, PathInits inits) {
        this(ImageFile.class, metadata, inits);
    }

    public QImageFile(Class<? extends ImageFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.registerRecipe = inits.isInitialized("registerRecipe") ? new QRegisterRecipe(forProperty("registerRecipe"), inits.get("registerRecipe")) : null;
    }

}

