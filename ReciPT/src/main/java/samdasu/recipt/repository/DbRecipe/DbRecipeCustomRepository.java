package samdasu.recipt.repository.DbRecipe;

import com.querydsl.core.Tuple;
import samdasu.recipt.entity.DbRecipe;

import java.util.List;

public interface DbRecipeCustomRepository {
    void addDbLikeCount(DbRecipe dbRecipe); //db 레시피 안에서 좋아요

    void subDbLikeCount(DbRecipe dbRecipe);

    void addDbViewCount(DbRecipe dbRecipe); //db 레시피 안에서 좋아요

    List<DbRecipe> findDbRecipeByContain(String searchingFoodName);

    List<DbRecipe> Top10DbRecipeView();

    List<DbRecipe> Top10DbRecipeLike();

    DbRecipe Top1DbRecipeLike();

    DbRecipe Top1DbRecipeViewCount();

    DbRecipe Top1DbRecipeRatingScore();

    List<DbRecipe> SearchingDbRecipeLikeByInputNum(int inputNum);

    List<DbRecipe> SearchingDbRecipeViewCountByInputNum(int inputNum);

    List<Tuple> Top10AllRecipeLike();
}