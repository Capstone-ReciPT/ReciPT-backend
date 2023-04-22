package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.entity.User;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    //    Optional<Heart> findByUserAndDbRecipe(User user, DbRecipe dbRecipe);
    @Query("select h from Heart h where h.user.userId = :userId and h.dbRecipe.dbRecipeId = :dbRecipeId")
    Optional<Heart> findUserAndDbRecipe(@Param("userId") Long userId, @Param("dbRecipeId") Long dbRecipeId);

    Optional<Heart> findByUserAndGptRecipe(User user, GptRecipe gptRecipe);
}
