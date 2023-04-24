package samdasu.recipt.controller.dto.Gpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptUpdateRatingScoreDto {
    private Double gptRatingScore;
    private Integer gptRatingPeople;

    public GptUpdateRatingScoreDto(Double gptRatingScore, Integer gptRatingPeople) {
        this.gptRatingScore = gptRatingScore;
        this.gptRatingPeople = gptRatingPeople;
    }

    public static GptUpdateRatingScoreDto createGptUpdateRatingScoreDto(Double gptRatingScore, Integer gptRatingPeople) {
        return new GptUpdateRatingScoreDto(gptRatingScore, gptRatingPeople);
    }
}
