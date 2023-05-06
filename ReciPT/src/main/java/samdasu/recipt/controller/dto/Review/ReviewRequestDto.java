package samdasu.recipt.controller.dto.Review;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewRequestDto {
    @NotNull
    private String comment;

    private Double inputRatingScore;


    public ReviewRequestDto(String comment, Double inputRatingScore) {
        this.comment = comment;
        this.inputRatingScore = inputRatingScore;
    }
    
}
