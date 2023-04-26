package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        User user = validateReview(reviewRequestDto);
        Review review = null;

        if (reviewRequestDto.getDbRecipe() == null) {
            review = Review.createGptReview(reviewRequestDto.getTitle(), reviewRequestDto.getComment(), 0, 0, user, reviewRequestDto.getGptRecipe());
        } else if (reviewRequestDto.getGptRecipe() == null) {
            review = Review.createDbReview(reviewRequestDto.getTitle(), reviewRequestDto.getComment(), 0, 0, user, reviewRequestDto.getDbRecipe());
        }
        return reviewRepository.save(review).getReviewId();
    }

    private User validateReview(ReviewRequestDto reviewRequestDto) {
        reviewRepository.findByTitle(reviewRequestDto.getTitle())
                .ifPresent(review -> {
                    throw new IllegalArgumentException("Fail: Already Exist Review!");
                });
        User user = userRepository.findByUsername(reviewRequestDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        return user;
    }


    @Transactional
    public Long update(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        review.updateReviewInfo(reviewRequestDto);
        return reviewId;
    }

    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
        reviewRepository.delete(review);
    }


    /**
     * 조회 수 탑 10 조회
     */
    public List<Review> findTop10ViewCount() {
        return reviewRepository.Top10ReviewView();
    }

    /**
     * 좋아요 탑 10 조회
     */
    public List<Review> findTop10LikeCount() {
        return reviewRepository.Top10ReviewLike();
    }

    public Optional<Review> findReviewByTitle(String title) {
        return reviewRepository.findByTitle(title);

    }

    public List<Review> findReviewByWriter(String username) {
        List<Review> review = reviewRepository.findReviewByWriter(username);
        return review;
    }


    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
