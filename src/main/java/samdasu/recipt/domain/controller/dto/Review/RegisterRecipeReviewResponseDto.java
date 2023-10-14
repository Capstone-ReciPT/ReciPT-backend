package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Review;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRecipeReviewResponseDto {
    private Long registerRecipeId;
    @NotNull
    private String foodName;
    @NotNull
    private String username;
    @NotNull
    private String comment;
    private Integer likeCount;
    private Double ratingScore;
    private String thumbnailImage;
    private byte[] thumbnailImageByte;

    public RegisterRecipeReviewResponseDto(Review review) {
        registerRecipeId = review.getRegisterRecipe().getRegisterId();
        foodName = review.getRegisterRecipe().getFoodName();
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
