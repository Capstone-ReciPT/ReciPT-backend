package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;


@Getter
@Setter
public class RegisterHomeResponseDto {

    private Long recipeId;

    private String foodName;

    private byte[] thumbnailImage;

    private Integer likeCount;

    private Long viewCount;

    private Double ratingScore;


    public RegisterHomeResponseDto(RegisterRecipe recipe) {
        recipeId = recipe.getRegisterId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getRegisterRecipeThumbnail().getThumbnailData();
        likeCount = recipe.getLikeCount();
        viewCount = recipe.getViewCount();
        ratingScore = recipe.getRatingScore();
    }

    public static RegisterHomeResponseDto CreateRecipeHomeResponseDto(RegisterRecipe recipe) {
        return new RegisterHomeResponseDto(recipe);
    }

}