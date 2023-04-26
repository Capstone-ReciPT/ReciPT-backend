package samdasu.recipt.repository.DbRecipe;

import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;

import java.util.List;

public interface DbRecipeCustomRepository {
    void addDbLikeCount(DbRecipe dbRecipe); //db 레시피 안에서 좋아요

    void subDbLikeCount(DbRecipe dbRecipe);

    void addDbViewCount(DbRecipe dbRecipe); //db 레시피 안에서 좋아요

    List<DbRecipe> findDbRecipeByContain(String searchingFoodName);

    List<DbRecipe> Top10DbRecipeView();

    List<DbRecipe> Top10DbRecipeLike();

    List<Heart> Top10AllRecipeLike();
}