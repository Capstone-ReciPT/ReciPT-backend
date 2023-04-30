package samdasu.recipt.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;

@Component
@RequiredArgsConstructor
public class InsertRecipeService {

    private final DbRecipeRepository dbRecipeRepository;

    public void insertRecipe(DbRecipe recipe) {
        Allergy allergy = new Allergy(recipe.getAllergy().getCategory(), recipe.getAllergy().getCausedIngredient());
        DbRecipe dbRecipe = new DbRecipe(recipe.getDbFoodName(), recipe.getDbIngredient(), recipe.getHowToCook(),
                recipe.getThumbnailImage(), recipe.getDbContext(), recipe.getDbImage(),
                recipe.getDbLikeCount(), recipe.getDbViewCount(),
                recipe.getDbRatingScore(), recipe.getDbRatingPeople(), allergy);
        dbRecipeRepository.save(dbRecipe);
    }

}
