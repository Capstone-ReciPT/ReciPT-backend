package samdasu.recipt.repository.Review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.Review;

import java.util.List;

import static samdasu.recipt.entity.QRecipe.recipe;
import static samdasu.recipt.entity.QRegisterRecipe.registerRecipe;
import static samdasu.recipt.entity.QReview.review;
import static samdasu.recipt.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addReviewLikeCount(Review selectedReview) {
        queryFactory.update(review)
                .set(review.likeCount, review.likeCount.add(1))
                .where(review.eq(selectedReview))
                .execute();
    }


    @Override
    public void subReviewLikeCount(Review selectedReview) {
        queryFactory.update(review)
                .set(review.likeCount, review.likeCount.subtract(1))
                .where(review.eq(selectedReview))
                .execute();
    }

    @Override
    public List<Review> findReviewByWriter(String username) {
        return queryFactory
                .selectFrom(review)
                .join(review.user, user).fetchJoin()
                .where(review.user.username.eq(username))
                .fetch();
    }

    @Override
    public List<Review> registerOrderByLike(Long selectRegisterId) {
        return queryFactory
                .selectFrom(review)
                .join(registerRecipe.reviews, review).fetchJoin()
                .where(registerRecipe.registerId.eq(selectRegisterId))
                .orderBy(review.likeCount.desc())
                .fetch();
    }

    @Override
    public List<Review> registerOrderByCreateDate(Long selectRegisterId) {
        return queryFactory
                .selectFrom(review)
                .join(registerRecipe.reviews, review).fetchJoin()
                .where(registerRecipe.registerId.eq(selectRegisterId))
                .orderBy(registerRecipe.createDate.desc())
                .fetch();
    }

    @Override
    public List<Review> recipeOrderByLike(Long selectRecipeId) {
        return queryFactory
                .selectFrom(review)
                .join(recipe.review, review).fetchJoin()
                .where(recipe.recipeId.eq(selectRecipeId))
                .orderBy(review.likeCount.desc())
                .fetch();
    }

    @Override
    public List<Review> recipeOrderByCreateDate(Long selectRecipeId) {
        return queryFactory
                .selectFrom(review)
                .join(recipe.review, review).fetchJoin()
                .where(recipe.recipeId.eq(selectRecipeId))
                .orderBy(registerRecipe.createDate.desc())
                .fetch();
    }


}
