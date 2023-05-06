package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReviewHeartDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long reviewId;

    public ReviewHeartDto(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public static ReviewHeartDto createRecipeHeartDto(Long userId, Long reviewId) {
        return new ReviewHeartDto(userId, reviewId);
    }

    public ReviewHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        reviewId = heart.getReview().getReviewId();
    }
}


