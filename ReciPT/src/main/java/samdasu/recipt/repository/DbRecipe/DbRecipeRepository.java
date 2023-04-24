package samdasu.recipt.repository.DbRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;

import java.util.Optional;

public interface DbRecipeRepository extends JpaRepository<DbRecipe, Long>, DbRecipeCustomRepository {
    Optional<DbRecipe> findByDbFoodName(String dbFoodName);

    /**
     * Db+Gpt 레시피 조회 수 탑 10 조회
     * - join 실패...
     */
//    @Query(value = "select db.db_food_name, gpt.gpt_food_name from Db_Recipe db full outer join " +
//            "Gpt_Recipe gpt on db.recipe_id = gpt.gpt_id ",
////            countQuery = "select db from Db_Recipe db full outer join " +
////                    "Gpt_Recipe gpt on db.db_food_name = gpt.gpt_food_name ",
//            nativeQuery = true)
//    Page<RecipeProjection> Top10AllRecipeLike(Pageable pageable);

}
