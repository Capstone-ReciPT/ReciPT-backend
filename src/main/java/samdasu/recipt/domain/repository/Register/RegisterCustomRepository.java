package samdasu.recipt.domain.repository.Register;

import org.springframework.data.repository.query.Param;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.List;

public interface RegisterCustomRepository {
    List<RegisterRecipe> addRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> subRegisterRecipeLikeCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> addRegisterRecipeViewCount(RegisterRecipe registerRecipe);

    List<RegisterRecipe> dynamicSearching(String searchingFoodName, Integer likeCond, Long viewCond);

    List<RegisterRecipe> Top10Like();

    List<RegisterRecipe> Top10View();

    List<RegisterRecipe> Top10RatingScore();

    List<RegisterRecipe> Top10RecentRegister();

    void resetViewCount(LocalDateTime yesterday);

    List<String> RecommendByAge(@Param("userAge") int userAge);
}
