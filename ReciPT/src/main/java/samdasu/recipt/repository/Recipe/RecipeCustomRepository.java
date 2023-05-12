package samdasu.recipt.repository.Recipe;

import samdasu.recipt.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeCustomRepository {
    void addRecipeLikeCount(Recipe recipe); //db 레시피 안에서 좋아요

    void subRecipeLikeCount(Recipe recipe);

    void addRecipeViewCount(Recipe recipe); //db 레시피 안에서 좋아요

    List<Recipe> dynamicSearching(int likeCond, int viewCond, String searchingFoodName);

    List<Recipe> Top10RecipeLike();

    List<Recipe> Top10RecipeView();

    List<Recipe> Top10RecipeRatingScore();

    List<Recipe> Top10Like();

    List<Recipe> Top10View();

    List<Recipe> Top10RatingScore();

    List<Recipe> Top10RecentRegister();

    List<String> RecommendByAge(int inputAge);

    void resetViewCount(LocalDateTime yesterday);

//    List<Tuple> JoinTable();
}