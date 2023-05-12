package samdasu.recipt.controller.dto.Recipe;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Recipe;


@Getter
@Setter
public class RecipeShortResponseDto {

    private Long recipeId;

    private String foodName;

    private String thumbnailImage;

    private Integer likeCount;

    public RecipeShortResponseDto(Recipe recipe) {
        recipeId = recipe.getRecipeId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getThumbnailImage();
        likeCount = recipe.getLikeCount();
    }

    public static RecipeShortResponseDto CreateRecipeShortResponseDto(Recipe recipe) {
        return new RecipeShortResponseDto(recipe);
    }

}