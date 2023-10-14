package samdasu.recipt.domain.repository.Review;

import samdasu.recipt.domain.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> addReviewLikeCount(Review review);

    List<Review> subReviewLikeCount(Review review);

    List<Review> findRecipeReviews(Long selectRecipeId);

    List<Review> findRegisterRecipeReviews(Long selectRegisterId);

    List<Review> findReviewByWriter(String username);//글쓴이 조회

    List<Review> registerOrderByLike(Long selectRegisterId);

    List<Review> registerOrderByCreateDate(Long selectRegisterId);

    List<Review> recipeOrderByLike(Long selectRecipeId);

    List<Review> recipeOrderByCreateDate(Long selectRecipeId);

}