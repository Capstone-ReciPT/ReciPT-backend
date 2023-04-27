package samdasu.recipt.repository.RecentSearch;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.RecentSearch;
import samdasu.recipt.entity.User;

import java.util.Optional;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long>, RecentSearchCustomRepository {
    Optional<RecentSearch> findByUserAndDbRecipe(User user, DbRecipe dbRecipe);

    Optional<RecentSearch> findByUserAndGptRecipe(User user, GptRecipe gptRecipe);

    Optional<RecentSearch> findByUserAndDbRecipeAndGptRecipe(User user, DbRecipe dbRecipe, GptRecipe gptRecipe);

    Long countRecentSearchBy();

//    Optional<RecentSearch> findLastModifiedDateBy(List<RecentSearch> recentSearches);

}
