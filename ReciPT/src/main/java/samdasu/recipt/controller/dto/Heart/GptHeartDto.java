package samdasu.recipt.controller.dto.Heart;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Heart;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GptHeartDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private Long gptRecipeId;

    public GptHeartDto(Long userId, Long gptRecipeId) {
        this.userId = userId;
        this.gptRecipeId = gptRecipeId;
    }

    public static GptHeartDto createGptHeartDto(Long userId, Long gptRecipeId) {
        return new GptHeartDto(userId, gptRecipeId);
    }

    public GptHeartDto(Heart heart) {
        userId = heart.getUser().getUserId();
        gptRecipeId = heart.getGptRecipe().getGptRecipeId();
    }
}


