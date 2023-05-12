package samdasu.recipt.repository.Register;

import samdasu.recipt.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.List;

public interface RegisterCustomRepository {
    void addRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void subRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    void addRegisterRecipeViewCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> dynamicSearching(int likeCond, int viewCond, String searchingFoodName);

    List<RegisterRecipe> Top10Like();

    List<RegisterRecipe> Top10View();

    List<RegisterRecipe> Top10RatingScore();

    List<RegisterRecipe> Top10RecentRegister();

    List<String> RecommendByAge(int inputAge);

    void resetViewCount(LocalDateTime yesterday);


//    RegisterRecipe Top1registerRecipeLike();
//
//    RegisterRecipe Top1registerRecipeViewCount();
//
//    RegisterRecipe Top1registerRecipeRatingScore();
//

}
