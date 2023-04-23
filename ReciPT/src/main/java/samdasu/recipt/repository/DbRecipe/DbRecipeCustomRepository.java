package samdasu.recipt.repository.DbRecipe;

import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;

import java.util.List;

public interface DbRecipeCustomRepository {
    void addDbLikeCount(DbRecipe dbRecipe); //db 레시피 안에서 좋아요

    void subDbLikeCount(DbRecipe dbRecipe);

    List<DbRecipe> Top10DbRecipeView(DbRecipe dbRecipe);

    List<DbRecipe> Top10DbRecipeLike(DbRecipe dbRecipe);

    List<Heart> Top10AllRecipeLike();
}