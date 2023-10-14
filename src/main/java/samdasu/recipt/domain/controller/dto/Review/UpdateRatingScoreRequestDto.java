package samdasu.recipt.domain.controller.dto.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRatingScoreRequestDto {

    private Double inputRatingScore;

    public UpdateRatingScoreRequestDto(Double inputRatingScore) {
        this.inputRatingScore = inputRatingScore;
    }

    public static UpdateRatingScoreRequestDto createUpdateRatingScoreRequestDto(Double inputRatingScore) {
        return new UpdateRatingScoreRequestDto(inputRatingScore);
    }
}
