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

    public RecipeHeartDto(Long userId, Long recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public static RecipeHeartDto createRecipeHeartDto(Long userId, Long recipeId) {
        return new RecipeHeartDto(userId, recipeId);
    }

    public RecipeHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        recipeId = heart.getRecipe().getRecipeId();
    }
}


