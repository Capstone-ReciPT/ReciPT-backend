package samdasu.recipt.repository.GptRecipe;

import samdasu.recipt.entity.GptRecipe;

import java.util.List;

public interface GptRecipeCustomRepository {
    void addGptLikeCount(GptRecipe gptRecipe);

    void subGptLikeCount(GptRecipe gptRecipe);

    void addGptViewCount(GptRecipe gptRecipe);

    List<GptRecipe> findGptRecipeByContain(String searchingFoodName);

    List<GptRecipe> Top10GptRecipeLike();

    List<GptRecipe> Top10GptRecipeView();

    GptRecipe Top1GptRecipeLike();

    GptRecipe Top1GptRecipeViewCount();

    GptRecipe Top1GptRecipeRatingScore();

    List<GptRecipe> SearchingGptRecipeLikeByInputNum(int inputNum);

    List<GptRecipe> SearchingGptRecipeViewCountByInputNum(int inputNum);

}
