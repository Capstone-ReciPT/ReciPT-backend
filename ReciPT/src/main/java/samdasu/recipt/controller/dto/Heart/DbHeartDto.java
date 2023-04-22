package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

@Getter
@Setter
public class HeartDto {

    private Long userId;
    private Long dbRecipeId;

    private Long gptRecipeId;

    public HeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        dbRecipeId = heart.getDbRecipe().getDbRecipeId();
        gptRecipeId = heart.getGptRecipe().getGptRecipeId();
    }
}
