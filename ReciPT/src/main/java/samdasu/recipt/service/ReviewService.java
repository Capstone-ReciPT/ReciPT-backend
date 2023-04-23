package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.controller.dto.Review.ReviewResponseDto;
import samdasu.recipt.entity.Review;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.Review.ReviewRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageFileService imageFileService;

    @Transactional
    public void IncreaseViewCount(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.addViewCount(review);
    }

    @Transactional
    public void IncreaseReviewLike(ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewRequestDto.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.addReviewLike(review);
    }

    @Transactional
    public void DecreaseReviewLike(ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewRequestDto.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.subReviewLike(review);
    }

    @Transactional
    public Long saveReview(ReviewRequestDto reviewRequestDto) {
        validateReview(reviewRequestDto);
        Review review = Review.createReview(reviewRequestDto.getTitle(), reviewRequestDto.getComment(), reviewRequestDto.getViewCount(), reviewRequestDto.getLikeCount(), reviewRequestDto.getUser(), reviewRequestDto.getGptRecipe(), reviewRequestDto.getDbRecipe());
        return reviewRepository.save(review).getReviewId();
        /**
         * mapper 써서 리뷰 저장하는 방식
         * - test 돌려보고 결과 안 좋으면 사용할 것!
         */
//        User user = userRepository.findById(reviewRequestDto.getUser().getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
//
//        Review review = reviewRequestMapper.toEntity(reviewRequestDto);
//        review.updateUser();
    }

    private void validateReview(ReviewRequestDto reviewRequestDto) {
        reviewRepository.findById(reviewRequestDto.getReviewId())
                .ifPresent(review -> {
                    throw new IllegalArgumentException("Fail: Already Exist Review!");
                });
    }

    @Transactional
    public Long update(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.updateReviewInfo(reviewRequestDto);
        return reviewId;
    }

    @Transactional
    public Review delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        reviewRepository.delete(review);
        return review;
    }


    /**
     * 조회 수 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<Review> findTop10ViewCount(Review review) {
        return reviewRepository.Top10ReviewView(review);
    }

    /**
     * 좋아요 탑 10 조회
     */
    @Transactional(readOnly = true)
    public List<Review> findTop10LikeCount(Review review) {
        return reviewRepository.Top10ReviewLike(review);
    }

    @Transactional(readOnly = true)
    public Review findReviewByTitle(String title) {
        return reviewRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Review> findReviewByWriter(String username) {
        List<Review> review = reviewRepository.findReviewByWriter(username);
        return review;
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No Review Info"));
        return new ReviewResponseDto(review);
    }

    @Transactional(readOnly = true)
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
