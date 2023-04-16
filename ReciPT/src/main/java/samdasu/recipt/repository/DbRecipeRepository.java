package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;

public interface DbRecipeRepository extends JpaRepository<DbRecipe, Long> {
}
