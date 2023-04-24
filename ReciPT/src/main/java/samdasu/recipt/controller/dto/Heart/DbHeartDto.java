package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DbHeartDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long dbRecipeId;

    public DbHeartDto(Long userId, Long dbRecipeId) {
        this.userId = userId;
        this.dbRecipeId = dbRecipeId;
    }

    public static DbHeartDto createDbHeartDto(Long userId, Long dbRecipeId) {
        return new DbHeartDto(userId, dbRecipeId);
    }

    public DbHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        dbRecipeId = heart.getDbRecipe().getDbRecipeId();
    }
}


