package samdasu.recipt.repository.Recipe;

import samdasu.recipt.entity.Recipe;

import java.util.List;

public interface RecipeCustomRepository {
    void addRecipeLikeCount(Recipe recipe); //db 레시피 안에서 좋아요

    void subRecipeLikeCount(Recipe recipe);

    void addRecipeViewCount(Recipe recipe); //db 레시피 안에서 좋아요

    List<Recipe> findRecipeByContain(String searchingFoodName);

    List<Recipe> SearchingRecipeLikeByInputNum(int inputNum);

    List<Recipe> SearchingRecipeViewCountByInputNum(int inputNum);


//
//    List<DbRecipe> Top10DbRecipeView();
//
//    List<DbRecipe> Top10DbRecipeLike();
//
//    DbRecipe Top1DbRecipeLike();
//
//    DbRecipe Top1DbRecipeViewCount();
//
//    DbRecipe Top1DbRecipeRatingScore();
//
//    List<Tuple> Top10AllRecipeLike();
}