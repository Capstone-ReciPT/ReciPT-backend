package samdasu.recipt.repository.Review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.QReview;
import samdasu.recipt.entity.Review;

import java.util.List;

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
    public List<Review> Top10ReviewView() {
        List<Review> top10View = queryFactory
                .selectFrom(QReview.review)
                .orderBy(QReview.review.viewCount.desc())
                .limit(10)
                .fetch();
        return top10View;
    }

    @Override
    public List<Review> Top10ReviewLike() {
        List<Review> top10Like = queryFactory
                .selectFrom(QReview.review)
                .orderBy(QReview.review.likeCount.desc())
                .limit(10)
                .fetch();
        return top10Like;
    }

    @Override
    public List<Review> findReviewByWriter(String username) {
        List<Review> result = queryFactory
                .selectFrom(review)
                .join(review.user, user).fetchJoin()
                .where(review.user.username.eq(username))
                .fetch();
        return result;
    }


}
