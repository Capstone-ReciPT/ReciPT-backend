package samdasu.recipt.repository.GptRecipe;

import samdasu.recipt.entity.GptRecipe;

import java.util.List;

public interface GptRecipeCustomRepository {
    void addGptLikeCount(GptRecipe gptRecipe);

    void subGptLikeCount(GptRecipe gptRecipe);

    List<GptRecipe> Top10GptRecipeLike(GptRecipe gptRecipe);
}
