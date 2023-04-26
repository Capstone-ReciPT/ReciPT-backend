package samdasu.recipt.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.Heart.GptHeartDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GptRequestDto {
    @NotNull
    private String gptFoodName;
    @NotNull
    private String gptIngredient;
    @NotNull
    private String gptHowToCook;
    private String gptTip;
    private Double gptRatingScore;
    private Allergy allergy;
    private List<GptHeartDto> hearts;

    public GptRequestDto(GptRecipe gptRecipe) {
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
        gptRatingScore = gptRecipe.getGptRatingScore();
        allergy = gptRecipe.getAllergy();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new GptHeartDto(heart))
                .collect(Collectors.toList());
    }

    public GptRequestDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Double gptRatingScore, Allergy allergy) {
        this.gptFoodName = gptFoodName;
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
        this.allergy = allergy;
        this.gptRatingScore = gptRatingScore;
    }

    public static GptRequestDto createGptDto(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Double gptRatingScore, Allergy allergy) {
        return new GptRequestDto(gptFoodName, gptIngredient, gptHowToCook, gptTip, gptRatingScore, allergy);
    }

    public static GptRequestDto createGptDto(GptRecipe gptRecipe) {
        return new GptRequestDto(gptRecipe.getGptFoodName(), gptRecipe.getGptIngredient(), gptRecipe.getGptHowToCook(), gptRecipe.getGptTip(), gptRecipe.getGptRatingScore(), gptRecipe.getAllergy());
    }

}
