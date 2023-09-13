package samdasu.recipt.domain.repository.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeCustomRepository {
    Optional<Recipe> findByFoodName(String foodName);

    Optional<List<Recipe>> findByCategory(String category);

}
