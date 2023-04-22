package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.repository.DbRecipe.DbRecipeCustomRepository;

import java.util.List;
import java.util.Optional;

public interface GptRecipeService extends JpaRepository<DbRecipe, Long>, DbRecipeCustomRepository {
    Optional<DbRecipe> findByDbFoodName(String dbFoodName);


    List<DbRecipe> findTop10RatingScoreBy();


}
