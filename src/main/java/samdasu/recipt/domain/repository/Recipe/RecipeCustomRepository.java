package samdasu.recipt.domain.repository.Recipe;

import samdasu.recipt.domain.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeCustomRepository {
    void addRecipeLikeCount(Recipe recipe); //db 레시피 안에서 좋아요

    void subRecipeLikeCount(Recipe recipe);

    void addRecipeViewCount(Recipe recipe); //db 레시피 안에서 좋아요

    List<Recipe> dynamicSearching(String searchingFoodName, Integer likeCond, Long viewCond);

    List<Recipe> Top10Like();

    List<Recipe> Top10View();

    List<Recipe> Top10RatingScore();

    List<Recipe> Top10RecentRegister();

    void resetViewCount(LocalDateTime yesterday);

    List<String> RecommendByRandH2(); //h2 DB

    List<String> RecommendByRandMySql(); //MySql DB
}