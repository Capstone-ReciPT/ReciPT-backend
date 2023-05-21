package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Review;

@Getter
@Setter
public class RecipeReviewResponseDto {
    private Long reviewId;
    private String username; //전체 리뷰에서는 필요, 마이 페이지에서는 필요 X
    private String comment;
    private Integer likeCount;
    private Double ratingScore;
    private String recipeThumbnailImage; //식품의약처 - 썸네일 사진(마이페이지)

    public RecipeReviewResponseDto(Review review) {
        reviewId = review.getReviewId();
        username = review.getUser().getUsername();
        comment = review.getComment();
        likeCount = review.getLikeCount();
        ratingScore = review.getRatingScore();
        recipeThumbnailImage = review.getRecipe().getThumbnailImage();
    }

    public static RecipeReviewResponseDto createRecipeReviewResponseDto(Review review) {
        return new RecipeReviewResponseDto(review);
    }

}
