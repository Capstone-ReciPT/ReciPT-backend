package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.DbReviewResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.Review.ReviewRepository;
import samdasu.recipt.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageFileService imageFileService;
    private final UserRepository userRepository;

    @Transactional
    public void increaseViewCount(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.addViewCount(review);
    }

    @Transactional
    public void increaseReviewLike(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.addReviewLike(review);
    }

    @Transactional
    public void decreaseReviewLike(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.subReviewLike(review);
    }

    @Transactional
    public Long saveReview(ReviewRequestDto reviewRequestDto) {
        validateReview(reviewRequestDto);
        Review review = null;
        User user = userRepository.findByUserName(reviewRequestDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));

        if (reviewRequestDto.getDbRecipe().getDbRecipeId() == null) {
            review = Review.createDbReview(reviewRequestDto.getTitle(), reviewRequestDto.getComment(), 0, 0, user, reviewRequestDto.getDbRecipe());
        } else if (reviewRequestDto.getGptRecipe().getGptRecipeId() == null) {
            review = Review.createGptReview(reviewRequestDto.getTitle(), reviewRequestDto.getComment(), 0, 0, user, reviewRequestDto.getGptRecipe());
        }
        return reviewRepository.save(review).getReviewId();
    }

    private void validateReview(ReviewRequestDto reviewRequestDto) {
        reviewRepository.findByTitle(reviewRequestDto.getTitle())
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
    public Optional<Review> findReviewByTitle(String title) {
        return reviewRepository.findByTitle(title);

    }

    @Transactional(readOnly = true)
    public List<Review> findReviewByWriter(String username) {
        List<Review> review = reviewRepository.findReviewByWriter(username);
        return review;
    }

    @Transactional(readOnly = true)
    public DbReviewResponseDto findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail:No Review Info"));
        return new DbReviewResponseDto(review);
    }

    @Transactional(readOnly = true)
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
