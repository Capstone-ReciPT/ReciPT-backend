package samdasu.recipt.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.domain.entity.*;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserAndRecipe(User user, Recipe recipe);

    Optional<Heart> findByUserAndRegisterRecipe(User user, RegisterRecipe registerRecipe);

    Optional<Heart> findByUserAndReview(User user, Review review);

    Long countHeartBy();
}
