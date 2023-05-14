package samdasu.recipt.repository.Register;

import org.springframework.data.repository.query.Param;
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

    void resetViewCount(LocalDateTime yesterday);

    List<String> RecommendByAge(@Param("userAge") int userAge);
}
