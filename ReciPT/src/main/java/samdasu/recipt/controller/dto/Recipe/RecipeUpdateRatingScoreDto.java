package samdasu.recipt.controller.dto.Recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeUpdateRatingScoreDto {
    private Double ratingScore;
    private Integer ratingPeople;

    public RecipeUpdateRatingScoreDto(Double ratingScore, Integer ratingPeople) {
        this.ratingScore = ratingScore;
        this.ratingPeople = ratingPeople;
    }
    
}
