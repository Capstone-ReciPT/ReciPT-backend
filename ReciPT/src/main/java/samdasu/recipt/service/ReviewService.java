package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.Review;
import samdasu.recipt.repository.ReviewRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 조회 수 증가
     */
    @Transactional
    public void IncreaseViewCount(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        review.addViewCount();
    }

    /**
     * 조회 수 탑 10 조회
     */
    public void findTop10ViewCount() {
        List<Review> top10ViewCount = reviewRepository.findTop10ViewCountBy();
    }
}
