package samdasu.recipt.controller.dto.RecentSearch;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.RecentSearch;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DbRecentSearchDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private Long dbRecipeId;

    public DbRecentSearchDto(RecentSearch recentSearch) {
        userId = recentSearch.getUser().getUserId();
        dbRecipeId = recentSearch.getDbRecipe().getDbRecipeId();
    }

    public DbRecentSearchDto(Long userId, Long dbRecipeId) {
        this.userId = userId;
        this.dbRecipeId = dbRecipeId;
    }

    public static DbRecentSearchDto createDbHeartDto(Long userId, Long dbRecipeId) {
        return new DbRecentSearchDto(userId, dbRecipeId);
    }
}


