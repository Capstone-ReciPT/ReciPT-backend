package samdasu.recipt.repository.Register;

import samdasu.recipt.entity.RegisterRecipe;

import java.util.List;

public interface RegisterCustomRepository {
    void addRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void subRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void addRegisterRecipeViewCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> findRegisterRecipeByContain(String searchingFoodName);

    List<RegisterRecipe> SearchingRegisterRecipeLikeByInputNum(int inputNum);

    List<RegisterRecipe> SearchingRegisterRecipeViewCountByInputNum(int inputNum);
    

//    List<RegisterRecipe> Top10registerRecipeLike();
//
//    List<RegisterRecipe> Top10registerRecipeView();
//
//    RegisterRecipe Top1registerRecipeLike();
//
//    RegisterRecipe Top1registerRecipeViewCount();
//
//    RegisterRecipe Top1registerRecipeRatingScore();
//

}