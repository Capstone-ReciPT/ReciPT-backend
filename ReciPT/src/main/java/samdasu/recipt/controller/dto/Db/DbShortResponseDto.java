package samdasu.recipt.controller.dto.Db;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.DbRecipe;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DbShortResponseDto {
    @NotNull
    private String dbFoodName;
    @NotNull
    private String thumbnailImage;
    private Integer dbLikeCount;
    private Double dbRatingResult;
    private Integer dbRatingPeople;

    public DbShortResponseDto(DbRecipe dbRecipe) {
        dbFoodName = dbRecipe.getDbFoodName();
        thumbnailImage = dbRecipe.getThumbnailImage();
        dbLikeCount = dbRecipe.getDbLikeCount();
        dbRatingResult = dbRecipe.getDbRatingScore();
        dbRatingPeople = dbRecipe.getDbRatingPeople();
    }

    public static DbShortResponseDto createShortInfo(DbRecipe dbRecipe) {
        return new DbShortResponseDto(dbRecipe);
    }
}
