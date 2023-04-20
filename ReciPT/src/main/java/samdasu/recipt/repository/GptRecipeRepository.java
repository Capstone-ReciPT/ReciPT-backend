package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.GptRecipe;

import java.util.List;
import java.util.Optional;

public interface GptRecipeRepository extends JpaRepository<GptRecipe, Long> {
    Optional<GptRecipe> findByGptFoodName(String gptFoodName);

    List<GptRecipe> findTop10ViewCountBy();
}
