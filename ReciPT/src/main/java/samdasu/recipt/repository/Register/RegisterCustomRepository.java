package samdasu.recipt.repository.Register;

import samdasu.recipt.entity.RegisterRecipe;

import java.util.List;

public interface RegisterCustomRepository {
    void addRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void subRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void addRegisterRecipeViewCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> dynamicSearching(int likeCond, int viewCond, String searchingFoodName);

    List<RegisterRecipe> Top10RegisterRecipeLike();

    List<RegisterRecipe> Top10RegisterRecipeView();

    List<RegisterRecipe> Top10RegisterRecipeRatingScore();


//    RegisterRecipe Top1registerRecipeLike();
//
//    RegisterRecipe Top1registerRecipeViewCount();
//
//    RegisterRecipe Top1registerRecipeRatingScore();
//

}
