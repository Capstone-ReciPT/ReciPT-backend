package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.RecentSearch;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RecentSearchDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private Long gptRecipeId;
    @NotBlank
    private Long dbRecipeId;

    public RecentSearchDto(RecentSearch recentSearch) {
        userId = recentSearch.getUser().getUserId();
        dbRecipeId = recentSearch.getDbRecipe().getDbRecipeId();
        gptRecipeId = recentSearch.getGptRecipe().getGptRecipeId();
    }

    public RecentSearchDto(Long userId, Long dbRecipeId, Long gptRecipeId) {
        this.userId = userId;
        this.dbRecipeId = dbRecipeId;
        this.gptRecipeId = gptRecipeId;
    }

    public static RecentSearchDto createDbHeartDto(Long userId, Long dbRecipeId, Long gptRecipeId) {
        return new RecentSearchDto(userId, dbRecipeId, gptRecipeId);
    }
}


