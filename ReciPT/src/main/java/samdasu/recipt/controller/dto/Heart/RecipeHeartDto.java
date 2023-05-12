package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RecipeHeartDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long recipeId;

    private String foodName;

    public RecipeHeartDto(Long userId, Long recipeId, String foodName) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.foodName = foodName;
    }

    public static RecipeHeartDto createRecipeHeartDto(Long userId, Long recipeId, String foodName) {
        return new RecipeHeartDto(userId, recipeId, foodName);
    }

    public RecipeHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        recipeId = heart.getRecipe().getRecipeId();
        foodName = heart.getRecipe().getFoodName();
    }
}


