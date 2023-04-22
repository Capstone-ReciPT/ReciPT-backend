package samdasu.recipt.repository.GptRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.GptRecipe;

import java.util.Optional;

public interface GptRecipeRepository extends JpaRepository<GptRecipe, Long>, GptRecipeCustomRepository {
    Optional<GptRecipe> findByGptFoodName(String gptFoodName);

}
