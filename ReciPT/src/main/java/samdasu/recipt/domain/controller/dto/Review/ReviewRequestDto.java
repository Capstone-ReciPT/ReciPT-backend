package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull
    private String comment;

    private Double inputRatingScore;

    public ReviewRequestDto(String comment, Double inputRatingScore) {
        this.comment = comment;
        this.inputRatingScore = inputRatingScore;
    }

    public static ReviewRequestDto createReviewRequestDto(String comment, Double inputRatingScore) {
        return new ReviewRequestDto(comment, inputRatingScore);
    }

}
