package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;


@Getter
@Setter
public class RegisterRecipeShortResponseDto {
    private Long recipeId;
    private String foodName;
    private Integer likeCount;
    private String category;
    private String thumbnailImage;

    public RegisterRecipeShortResponseDto(RegisterRecipe recipe) {
        recipeId = recipe.getRegisterId();
        foodName = recipe.getFoodName();
        likeCount = recipe.getLikeCount();
        category = recipe.getCategory();
        thumbnailImage = recipe.getThumbnailImage();
    }

    public static RegisterRecipeShortResponseDto CreateRecipeShortResponseDto(RegisterRecipe recipe) {
        return new RegisterRecipeShortResponseDto(recipe);
    }
}