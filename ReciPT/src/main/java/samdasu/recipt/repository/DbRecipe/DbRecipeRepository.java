package samdasu.recipt.repository.DbRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;

import java.util.Optional;

public interface DbRecipeRepository extends JpaRepository<DbRecipe, Long>, DbRecipeCustomRepository {
    Optional<DbRecipe> findByDbFoodName(String dbFoodName);
}
