package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

@Getter
@Setter
public class DbHeartDto {

    private Long userId;
    private Long dbRecipeId;

    private Long gptRecipeId;

    public DbHeartDto(Long userId, Long dbRecipeId) {
        this.userId = userId;
        this.dbRecipeId = dbRecipeId;
    }

    public static DbHeartDto createDbHeart(Long userId, Long dbRecipeId) {
        return new DbHeartDto(userId, dbRecipeId);
    }

    public DbHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        dbRecipeId = heart.getDbRecipe().getDbRecipeId();
        gptRecipeId = heart.getGptRecipe().getGptRecipeId();
    }
}


