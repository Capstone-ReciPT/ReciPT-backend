package samdasu.recipt.repository.DbRecipe;

import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;

import java.util.List;

public interface DbRecipeCustomRepository {
    void addDbLikeCount(DbRecipe dbRecipe);

    void subDbLikeCount(DbRecipe dbRecipe);

    List<DbRecipe> Top10DbRecipeView(DbRecipe dbRecipe);

    List<DbRecipe> Top10DbRecipeLike(DbRecipe dbRecipe);

    List<Heart> Top10AllRecipeLike();
}