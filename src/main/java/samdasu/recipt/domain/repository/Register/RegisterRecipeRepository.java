package samdasu.recipt.domain.repository.Register;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.RegisterRecipe;

import java.util.List;
import java.util.Optional;

public interface RegisterRecipeRepository extends JpaRepository<RegisterRecipe, Long>, RegisterCustomRepository {
    Optional<RegisterRecipe> findByFoodName(String foodName);

    Optional<List<RegisterRecipe>> findByCategory(String category);
}
