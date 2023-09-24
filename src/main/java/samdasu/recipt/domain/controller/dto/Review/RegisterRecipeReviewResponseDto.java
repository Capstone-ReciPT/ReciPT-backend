package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Review;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRecipeReviewResponseDto {
    @NotEmpty
    private Long reviewId;
    @NotNull
    private String username; //전체 리뷰에서는 필요, 마이 페이지에서는 필요 X
    @NotNull
    private String comment;
    private Integer likeCount;

    private Double ratingScore;

    private String thumbnailImage;

    public RegisterRecipeReviewResponseDto(Review review) {
        reviewId = review.getReviewId();
        username = review.getUser().getUsername();
        comment = review.getComment();
        likeCount = review.getLikeCount();
        ratingScore = review.getRatingScore();
        thumbnailImage = review.getRegisterRecipe().getThumbnailImage();
    }

    public static RegisterRecipeReviewResponseDto createRegisterRecipeReviewResponseDto(Review review) {
        return new RegisterRecipeReviewResponseDto(review);
    }
}
