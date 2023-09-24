package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;


@Getter
@Setter
public class RegisterHomeResponseDto {

    private Long recipeId;

    private String foodName;

    private Integer likeCount;

    private Long viewCount;

    private Double ratingScore;

    private String thumbnailImage;


    public RegisterHomeResponseDto(RegisterRecipe recipe) {
        recipeId = recipe.getRegisterId();
        foodName = recipe.getFoodName();
        likeCount = recipe.getLikeCount();
        viewCount = recipe.getViewCount();
        ratingScore = recipe.getRatingScore();
        thumbnailImage = recipe.getThumbnailImage();
    }

    public static RegisterHomeResponseDto CreateRecipeHomeResponseDto(RegisterRecipe recipe) {
        return new RegisterHomeResponseDto(recipe);
    }

}