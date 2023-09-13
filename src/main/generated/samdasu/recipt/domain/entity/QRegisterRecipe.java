package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegisterRecipe is a Querydsl query type for RegisterRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegisterRecipe extends EntityPathBase<RegisterRecipe> {

    private static final long serialVersionUID = 1053815475L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegisterRecipe registerRecipe = new QRegisterRecipe("registerRecipe");

    public final samdasu.recipt.domain.common.QBaseTimeEntity _super = new samdasu.recipt.domain.common.QBaseTimeEntity(this);

    public final StringPath category = createString("category");

    public final StringPath comment = createString("comment");

    public final StringPath context = createString("context");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath foodName = createString("foodName");

    public final QGpt gpt;

    public final ListPath<Heart, QHeart> hearts = this.<Heart, QHeart>createList("hearts", Heart.class, QHeart.class, PathInits.DIRECT2);

    public final ListPath<ImageFile, QImageFile> imageFiles = this.<ImageFile, QImageFile>createList("imageFiles", ImageFile.class, QImageFile.class, PathInits.DIRECT2);

    public final StringPath ingredient = createString("ingredient");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> ratingPeople = createNumber("ratingPeople", Integer.class);

    public final NumberPath<Double> ratingScore = createNumber("ratingScore", Double.class);

    public final NumberPath<Long> registerId = createNumber("registerId", Long.class);

    public final QRegisterRecipeThumbnail registerRecipeThumbnail;

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QRegisterRecipe(String variable) {
        this(RegisterRecipe.class, forVariable(variable), INITS);
    }

    public QRegisterRecipe(Path<? extends RegisterRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegisterRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegisterRecipe(PathMetadata metadata, PathInits inits) {
        this(RegisterRecipe.class, metadata, inits);
    }

    public QRegisterRecipe(Class<? extends RegisterRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gpt = inits.isInitialized("gpt") ? new QGpt(forProperty("gpt"), inits.get("gpt")) : null;
        this.registerRecipeThumbnail = inits.isInitialized("registerRecipeThumbnail") ? new QRegisterRecipeThumbnail(forProperty("registerRecipeThumbnail"), inits.get("registerRecipeThumbnail")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

