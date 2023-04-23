package samdasu.recipt.controller.dto.Db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbUpdateRatingScoreDto {
    private Double dbRatingScore;
    private Integer dbRatingPeople;

    public DbUpdateRatingScoreDto(Double dbRatingScore, Integer dbRatingPeople) {
        this.dbRatingScore = dbRatingScore;
        this.dbRatingPeople = dbRatingPeople;
    }

    public static DbUpdateRatingScoreDto createDbUpdateRatingScoreDto(Double dbRatingScore, Integer dbRatingPeople) {
        return new DbUpdateRatingScoreDto(dbRatingScore, dbRatingPeople);
    }
}
