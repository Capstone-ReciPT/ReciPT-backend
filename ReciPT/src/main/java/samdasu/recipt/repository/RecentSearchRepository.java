package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.RecentSearch;
import samdasu.recipt.entity.User;

import java.util.List;
import java.util.Optional;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    Optional<RecentSearch> findByUserAndDbRecipe(User user, DbRecipe dbRecipe);

    Optional<RecentSearch> findByUserAndGptRecipe(User user, GptRecipe gptRecipe);
    
    List<RecentSearch> findByUser(User user);

    Long countRecentSearchBy();
}
