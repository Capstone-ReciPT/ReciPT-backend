package samdasu.recipt.domain.controller.dto.Recipe;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Recipe;


@Getter
@Setter
public class RecipeHomeResponseDto {

    private Long recipeId;

    private String foodName;

    private String thumbnailImage;

    private Integer likeCount;

    private Long viewCount;

    private Double ratingScore;


    public RecipeHomeResponseDto(Recipe recipe) {
        recipeId = recipe.getRecipeId();
        foodName = recipe.getFoodName();
        thumbnailImage = recipe.getThumbnailImage();
        likeCount = recipe.getLikeCount();
        viewCount = recipe.getViewCount();
        ratingScore = recipe.getRatingScore();
    }

    public static RecipeHomeResponseDto CreateRecipeHomeResponseDto(Recipe recipe) {
        return new RecipeHomeResponseDto(recipe);
    }

}