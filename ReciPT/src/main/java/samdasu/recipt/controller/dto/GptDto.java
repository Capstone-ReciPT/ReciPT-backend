package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GptDto {

    private Long gptRecipeId;
    private String gptFoodName;
    private String gptIngredient;
    private String gptHowToCook;
    private String gptTip;
    private Integer gptViewCount; //조회 수

    private Integer gptLikeCount; //레시피 좋아요
    private Allergy allergy;
    private Review review;
    private List<GptHeartDto> hearts;

    public GptDto(GptRecipe gptRecipe) {
        gptRecipeId = gptRecipe.getGptRecipeId();
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
//        gptLikeCount = gptRecipe.getGptLikeCount();
        allergy = gptRecipe.getAllergy();
        review = gptRecipe.getReview();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
    }

    public GptDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Allergy allergy, Integer gptLikeCount, Integer gptViewCount) {
        this.gptFoodName = gptFoodName;
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
        this.allergy = allergy;
        this.gptLikeCount = gptLikeCount;
        this.gptViewCount = gptViewCount;
        this.review = review;
        this.hearts = hearts;
    }

    public static GptDto createGptDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Allergy allergy, Integer gptLikeCount, Integer gptViewCount) {
        return new GptDto(gptFoodName, gptIngredient, gptHowToCook, gptTip, allergy, gptLikeCount, gptViewCount);
    }

    public static GptDto createGptDto(GptRecipe gptRecipe) {
        return new GptDto(gptRecipe.getGptFoodName(), gptRecipe.getGptIngredient(), gptRecipe.getGptHowToCook(), gptRecipe.getGptTip(), gptRecipe.getAllergy(), gptRecipe.getGptLikeCount(), gptRecipe.getGptViewCount());
    }

}
