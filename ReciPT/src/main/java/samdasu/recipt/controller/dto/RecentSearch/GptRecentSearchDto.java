package samdasu.recipt.controller.dto.RecentSearch;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.RecentSearch;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GptRecentSearchDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private Long gptRecipeId;

    public GptRecentSearchDto(RecentSearch recentSearch) {
        userId = recentSearch.getUser().getUserId();
        gptRecipeId = recentSearch.getGptRecipe().getGptRecipeId();
    }

    public GptRecentSearchDto(Long userId, Long gptRecipeId) {
        this.userId = userId;
        this.gptRecipeId = gptRecipeId;
    }

    public static GptRecentSearchDto createDbHeartDto(Long userId, Long gptRecipeId) {
        return new GptRecentSearchDto(userId, gptRecipeId);
    }
}


