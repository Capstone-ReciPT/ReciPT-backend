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
public class GptRequestDto {
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
    private Double gptRatingScore;
    private Integer gptRatingPeople;
    private Allergy allergy;
    private Review review;
    private List<GptHeartDto> hearts;

    public GptRequestDto(GptRecipe gptRecipe) {
        gptRecipeId = gptRecipe.getGptRecipeId();
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
        gptViewCount = gptRecipe.getGptViewCount();
        gptLikeCount = gptRecipe.getGptLikeCount();
        gptRatingScore = gptRecipe.getGptRatingScore();
        gptRatingPeople = gptRecipe.getGptRatingPeople();
        allergy = gptRecipe.getAllergy();
        review = gptRecipe.getReview();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
    }

    public GptRequestDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Allergy allergy, Integer gptLikeCount, Integer gptViewCount, Double gptRatingScore, Integer gptRatingPeople) {
        this.gptFoodName = gptFoodName;
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
        this.allergy = allergy;
        this.gptLikeCount = gptLikeCount;
        this.gptViewCount = gptViewCount;
        this.gptRatingScore = gptRatingScore;
        this.gptRatingPeople = gptRatingPeople;
        this.review = review;
        this.hearts = hearts;
    }

    public static GptRequestDto createGptDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Allergy allergy, Integer gptLikeCount, Integer gptViewCount, Double gptRatingScore, Integer gptRatingPeople) {
        return new GptRequestDto(gptFoodName, gptIngredient, gptHowToCook, gptTip, allergy, gptLikeCount, gptViewCount, gptRatingScore, gptRatingPeople);
    }

    public static GptRequestDto createGptDto(GptRecipe gptRecipe) {
        return new GptRequestDto(gptRecipe.getGptFoodName(), gptRecipe.getGptIngredient(), gptRecipe.getGptHowToCook(), gptRecipe.getGptTip(), gptRecipe.getAllergy(), gptRecipe.getGptLikeCount(), gptRecipe.getGptViewCount(), gptRecipe.getGptRatingScore(), gptRecipe.getGptRatingPeople());
    }

}
