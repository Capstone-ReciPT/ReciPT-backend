package samdasu.recipt.controller.dto.Init;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

@Getter
@Setter
public class HeartDto {

    private Long userId;
    private Long dbRecipeId;

    public HeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        dbRecipeId = heart.getDbRecipe().getDbRecipeId();
    }
}
