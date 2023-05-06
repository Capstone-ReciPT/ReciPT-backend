package samdasu.recipt.controller.dto.Register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUpdateRatingScoreDto {
    private Double ratingScore;
    private Integer ratingPeople;

    public RegisterUpdateRatingScoreDto(Double ratingScore, Integer ratingPeople) {
        this.ratingScore = ratingScore;
        this.ratingPeople = ratingPeople;
    }
    
}
