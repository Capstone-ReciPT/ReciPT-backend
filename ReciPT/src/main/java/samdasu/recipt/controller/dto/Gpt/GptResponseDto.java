package samdasu.recipt.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GptResponseDto {

    @NotEmpty
    private Long gptRecipeId;
    @NotNull
    private String gptFoodName;
    @NotNull
    private String gptIngredient;
    @NotNull
    private String gptHowToCook;
    private String gptTip;
    private Integer gptViewCount; //조회 수
    private Integer gptLikeCount; //레시피 좋아요
    private Double dbRatingResult;
    private Allergy allergy;
    private Review review;
    private List<GptHeartDto> hearts;

    public GptResponseDto(GptRecipe gptRecipe) {
        gptRecipeId = gptRecipe.getGptRecipeId();
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
        gptViewCount = gptRecipe.getGptViewCount();
        gptLikeCount = gptRecipe.getGptLikeCount();
        allergy = gptRecipe.getAllergy();
        review = gptRecipe.getReview();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
    }

    public static GptResponseDto createGptResponseDto(GptRecipe gptRecipe) {
        return new GptResponseDto(gptRecipe);
    }


}
