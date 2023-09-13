package samdasu.recipt.domain.controller.dto.Recipe;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Recipe;


@Getter
@Setter
public class RecipeShortResponseDto {

    private Long recipeId;

    private String foodName;

    private String thumbnailImage;

    private Integer likeCount;

    private String category;

    public RecipeShortResponseDto(Recipe recipe) {
        recipeId = recipe.getRecipeId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getThumbnailImage();
        likeCount = recipe.getLikeCount();
        category = recipe.getCategory();
    }

    public static RecipeShortResponseDto createRecipeShortResponseDto(Recipe recipe) {
        return new RecipeShortResponseDto(recipe);
    }
}