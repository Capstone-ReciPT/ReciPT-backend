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
    private Integer gptLikeCount; //레시피 좋아요
    private Long gptViewCount; //조회 수
    private Double gptRatingResult;
    private Integer gptRatingPeople;
    private Allergy allergy;
    private List<Review> review;
    private List<GptHeartDto> hearts;

    public GptResponseDto(GptRecipe gptRecipe, Long gptRecipeId) {
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
        gptLikeCount = gptRecipe.getGptLikeCount();
        gptViewCount = gptRecipe.getGptViewCount();
        gptRatingResult = gptRecipe.getGptRatingScore();
        gptRatingPeople = gptRecipe.getGptRatingPeople();
        allergy = gptRecipe.getAllergy();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
    }

    public static GptResponseDto createGptResponseDto(GptRecipe gptRecipe, Long gptRecipeId) {
        return new GptResponseDto(gptRecipe, gptRecipeId);
    }


}
