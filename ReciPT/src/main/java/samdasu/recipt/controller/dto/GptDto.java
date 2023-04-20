package samdasu.recipt.controller.dto;

import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.Rating;
import samdasu.recipt.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

public class GptDto {

    private Long gptRecipeId;
    private String gptFoodName;
    private String gptIngredient;
    private String gptHowToCook;
    private String gptTip;
    private float gptRatingScore;
    private int gptRatingCount;
    private int gptLikeCount;
    private Allergy allergy;
    private Rating rating;
    private Review review;
    private List<HeartDto> hearts;

    public GptDto(GptRecipe gptRecipe) {
        gptRecipeId = gptRecipe.getGptRecipeId();
        gptFoodName = gptRecipe.getGptFoodName();
        gptIngredient = gptRecipe.getGptIngredient();
        gptHowToCook = gptRecipe.getGptHowToCook();
        gptTip = gptRecipe.getGptTip();
        gptRatingScore = gptRecipe.getGptRatingScore();
        gptRatingCount = gptRecipe.getGptRatingCount();
        gptLikeCount = gptRecipe.getGptLikeCount();
        allergy = gptRecipe.getAllergy();
        rating = gptRecipe.getRating();
        review = gptRecipe.getReview();
        hearts = gptRecipe.getHearts().stream()
                .map(heart -> new HeartDto(heart))
                .collect(Collectors.toList());
    }
}
