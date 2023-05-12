package samdasu.recipt.repository.Review;

import samdasu.recipt.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    void addReviewLikeCount(Review review);

    void subReviewLikeCount(Review review);

    List<Review> findReviewByWriter(String username);//글쓴이 조회

    List<Review> registerOrderByLike(Long selectRegisterId);

    List<Review> registerOrderByCreateDate(Long selectRegisterId);

    List<Review> recipeOrderByLike(Long selectRecipeId);

    List<Review> recipeOrderByCreateDate(Long selectRecipeId);

}