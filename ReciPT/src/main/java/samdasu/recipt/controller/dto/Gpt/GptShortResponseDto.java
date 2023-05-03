package samdasu.recipt.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.GptRecipe;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GptShortResponseDto {
    @NotNull
    private String gptFoodName;
    private Integer gptLikeCount; //레시피 좋아요
    private Double gptRatingResult;
    private Integer gptRatingPeople;

    public GptShortResponseDto(GptRecipe gptRecipe) {
        gptFoodName = gptRecipe.getGptFoodName();
        gptLikeCount = gptRecipe.getGptLikeCount();
        gptRatingResult = gptRecipe.getGptRatingScore();
        gptRatingPeople = gptRecipe.getGptRatingPeople();
    }

    public static GptShortResponseDto creatShortInfo(GptRecipe gptRecipe) {
        return new GptShortResponseDto(gptRecipe);
    }


}
