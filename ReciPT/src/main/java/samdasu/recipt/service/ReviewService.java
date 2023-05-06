package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.controller.dto.Review.ReviewUpdateRequestDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.entity.Review;
import samdasu.recipt.entity.User;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.Recipe.RecipeRepository;
import samdasu.recipt.repository.Register.RegisterRecipeRepository;
import samdasu.recipt.repository.Review.ReviewRepository;
import samdasu.recipt.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RegisterRecipeRepository registerRecipeRepository;

    @Transactional
    public Long saveRecipeReview(Long userId, Long reviewId, ReviewRequestDto reviewRequestDto) {
        //엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No User Info"));
        Recipe recipe = recipeRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Recipe Info"));

        Review review = Review.createRecipeReview(reviewRequestDto.getComment(), 0, reviewRequestDto.getInputRatingScore(), user, recipe);

        return reviewRepository.save(review).getReviewId();
    }

    @Transactional
    public Long update(Long reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        Review review = findById(reviewId);
        review.updateReviewInfo(reviewUpdateRequestDto);
        return reviewId;
    }

    @Transactional
    public void delete(Long reviewId) {
        Review review = findById(reviewId);
        reviewRepository.delete(review);
    }


    /**
     * 좋아요 많은 순
     */
    public List<Review> registerOrderByLike(Long selectRegisterId) {
        return reviewRepository.registerOrderByLike(selectRegisterId);
    }

    public List<Review> recipeOrderByLike(Long selectRecipeId) {
        return reviewRepository.recipeOrderByLike(selectRecipeId);
    }
    

    /**
     * 최신 순
     */
    public List<Review> registerOrderByCreateDate(Long selectRegisterId) {
        return reviewRepository.registerOrderByCreateDate(selectRegisterId);
    }

    public List<Review> recipeOrderByCreateDate(Long selectRecipeId) {
        return reviewRepository.recipeOrderByCreateDate(selectRecipeId);
    }


    public List<Review> findReviewByWriter(String username) {
        List<Review> review = reviewRepository.findReviewByWriter(username);
        return review;
    }

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

    private Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No Review Info"));
    }
}
