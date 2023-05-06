package samdasu.recipt.repository.Register;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.RegisterRecipe;

import java.util.Optional;

public interface RegisterRecipeRepository extends JpaRepository<RegisterRecipe, Long>, RegisterCustomRepository {
    Optional<RegisterRecipe> findByFoodName(String foodName);

}
