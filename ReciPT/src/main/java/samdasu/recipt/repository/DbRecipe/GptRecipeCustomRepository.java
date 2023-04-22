package samdasu.recipt.repository.DbRecipe;

import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;

public interface DBRecipeCustomRepository {
    void addDbLikeCount(DbRecipe dbRecipe);

    void addGptLikeCount(GptRecipe gptRecipe);

    void subDbLikeCount(DbRecipe dbRecipe);

    void subGptLikeCount(GptRecipe gptRecipe);
}
