package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.GptRecipe;

public interface GptRecipeRepository extends JpaRepository<GptRecipe, Long> {
}
