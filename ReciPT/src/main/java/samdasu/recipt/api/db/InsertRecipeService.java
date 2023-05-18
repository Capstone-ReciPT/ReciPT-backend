package samdasu.recipt.api.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.repository.Recipe.RecipeRepository;

@Service
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
