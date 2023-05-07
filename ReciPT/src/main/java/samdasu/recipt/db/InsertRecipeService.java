package samdasu.recipt.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.repository.Recipe.RecipeRepository;

@Component
@RequiredArgsConstructor
public class InsertRecipeService {

    private final RecipeRepository dbRecipeRepository;

    public void insertRecipe(Recipe recipe) {
        Recipe saveRecipe = new Recipe(recipe.getFoodName(), recipe.getIngredient(), recipe.getCategory(),
                recipe.getThumbnailImage(), recipe.getContext(), recipe.getImage(), recipe.getViewCount(),
                recipe.getLikeCount(), recipe.getRatingScore(), recipe.getRatingPeople());
        dbRecipeRepository.save(saveRecipe);
    }

}
