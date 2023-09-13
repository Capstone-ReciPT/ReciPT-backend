package samdasu.recipt.domain.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.domain.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RecipeHeartDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long recipeId;

    private String foodName;

    private String category;

    private String ingredient;

    public RecipeHeartDto(Long userId, Long recipeId, String foodName, String category, String ingredient) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.foodName = foodName;
        this.category = category;
        this.ingredient = ingredient;
    }

    public static RecipeHeartDto createRecipeHeartDto(Long userId, Long recipeId, String foodName, String category, String ingredient) {
        return new RecipeHeartDto(userId, recipeId, foodName, category, ingredient);
    }

    public RecipeHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        recipeId = heart.getRecipe().getRecipeId();
        foodName = heart.getRecipe().getFoodName();
        category = heart.getRecipe().getCategory();
        ingredient = heart.getRecipe().getIngredient();
    }
}


