package samdasu.recipt.domain.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.RegisterRecipe;


@Getter
@Setter
public class RegisterRecipeShortResponseDto {
    private Long recipeId;

    private String foodName;

    private byte[] thumbnailImage;

    private Integer likeCount;

    private String category;

    public RegisterRecipeShortResponseDto(RegisterRecipe recipe) {
        recipeId = recipe.getRegisterId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getRegisterRecipeThumbnail().getThumbnailData();
        likeCount = recipe.getLikeCount();
        category = recipe.getCategory();
    }

    public static RegisterRecipeShortResponseDto CreateRecipeShortResponseDto(RegisterRecipe recipe) {
        return new RegisterRecipeShortResponseDto(recipe);
    }
}