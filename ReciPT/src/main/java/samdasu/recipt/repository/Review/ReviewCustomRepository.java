package samdasu.recipt.repository.Review;

import samdasu.recipt.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    void addReviewLikeCount(Review review); //음식 종류에 상관없이 좋아요

    void subReviewLikeCount(Review review);

    List<Review> Top10ReviewView(Review review);

    List<Review> Top10ReviewLike(Review review);

    List<Review> findReviewByWriter(String userName);//글쓴이 조회

}