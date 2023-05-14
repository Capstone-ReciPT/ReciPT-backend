package samdasu.recipt.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.RegisterRecipe;


@Getter
@Setter
public class RegisterRecipeShortResponseDto {

    private Long recipeId;

    private String foodName;

    private byte[] thumbnailImage;

    private Integer likeCount;

    public RegisterRecipeShortResponseDto(RegisterRecipe recipe) {
        recipeId = recipe.getRegisterId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getRegisterRecipeThumbnail().getThumbnailData();
        likeCount = recipe.getLikeCount();
    }

    public static RegisterRecipeShortResponseDto CreateRecipeShortResponseDto(RegisterRecipe recipe) {
        return new RegisterRecipeShortResponseDto(recipe);
    }

}