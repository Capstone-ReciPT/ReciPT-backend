package samdasu.recipt.repository.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Recipe;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeCustomRepository {
    Optional<Recipe> findByFoodName(String foodName);
}
