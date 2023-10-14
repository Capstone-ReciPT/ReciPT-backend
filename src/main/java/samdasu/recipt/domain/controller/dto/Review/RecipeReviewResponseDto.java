package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Review;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RecipeReviewResponseDto {
    private Long recipeId;
    private String foodName;
    @NotNull
    private String username;
    @NotNull
    private String comment;
    private Integer likeCount;
    private Double ratingScore;
    private String recipeThumbnailImage; //식품의약처 - 썸네일 사진(마이페이지)

    public RecipeReviewResponseDto(Review review) {
        recipeId = review.getRecipe().getRecipeId();
        foodName = review.getRecipe().getFoodName();
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
