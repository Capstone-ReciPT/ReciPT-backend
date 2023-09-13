package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = -38969296L;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final samdasu.recipt.domain.common.QBaseTimeEntity _super = new samdasu.recipt.domain.common.QBaseTimeEntity(this);

    public final StringPath category = createString("category");

    public final StringPath context = createString("context");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath foodName = createString("foodName");

    public final ListPath<Heart, QHeart> hearts = this.<Heart, QHeart>createList("hearts", Heart.class, QHeart.class, PathInits.DIRECT2);

    public final StringPath image = createString("image");

    public final StringPath ingredient = createString("ingredient");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> ratingPeople = createNumber("ratingPeople", Integer.class);

    public final NumberPath<Double> ratingScore = createNumber("ratingScore", Double.class);

    public final NumberPath<Long> recipeId = createNumber("recipeId", Long.class);

    public final ListPath<Review, QReview> review = this.<Review, QReview>createList("review", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath thumbnailImage = createString("thumbnailImage");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QRecipe(String variable) {
        super(Recipe.class, forVariable(variable));
    }

    public QRecipe(Path<? extends Recipe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipe(PathMetadata metadata) {
        super(Recipe.class, metadata);
    }

}

