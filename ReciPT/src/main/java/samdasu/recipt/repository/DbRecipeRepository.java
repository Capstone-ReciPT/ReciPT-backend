package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;

import java.util.List;
import java.util.Optional;

public interface DbRecipeRepository extends JpaRepository<DbRecipe, Long> {
    Optional<DbRecipe> findByDbFoodName(String dbFoodName);

    List<DbRecipe> findTop10RatingScoreBy();


}
