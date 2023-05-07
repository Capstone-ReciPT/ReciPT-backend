package samdasu.recipt.repository.Recipe;

import samdasu.recipt.entity.Recipe;

import java.util.List;

public interface RecipeCustomRepository {
    void addRecipeLikeCount(Recipe recipe); //db 레시피 안에서 좋아요

    void subRecipeLikeCount(Recipe recipe);

    void addRecipeViewCount(Recipe recipe); //db 레시피 안에서 좋아요

    List<Recipe> findRecipeByContain(String searchingFoodName);

    List<Recipe> searchingRecipeLikeByInputNum(int inputNum);

    List<Recipe> searchingRecipeViewCountByInputNum(int inputNum);

    List<Recipe> dynamicSearching();
//    List<Tuple> JoinTable();
}