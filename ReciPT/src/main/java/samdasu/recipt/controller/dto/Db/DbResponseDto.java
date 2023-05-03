package samdasu.recipt.controller.dto.Db;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DbResponseDto {
    @NotEmpty
    private Long dbRecipeId;
    @NotNull
    private String dbFoodName;
    @NotNull
    private String dbIngredient;
    @NotNull
    private String howToCook;
    @NotNull
    private String thumbnailImage;
    @NotNull
    private String dbContext;
    @NotNull
    private String dbImage;
    private Integer dbLikeCount;
    private Long dbViewCount;
    private Double dbRatingResult;
    private Integer dbRatingPeople;
    private Allergy allergy;
    private List<DbHeartDto> hearts;
    private List<ReviewRequestDto> reviews;

    public DbResponseDto(DbRecipe dbRecipe) {
        dbFoodName = dbRecipe.getDbFoodName();
        dbIngredient = dbRecipe.getDbIngredient();
        howToCook = dbRecipe.getHowToCook();
        thumbnailImage = dbRecipe.getThumbnailImage();
        dbContext = dbRecipe.getDbContext();
        dbImage = dbRecipe.getDbImage();
        dbLikeCount = dbRecipe.getDbLikeCount();
        dbViewCount = dbRecipe.getDbViewCount();
        dbRatingResult = dbRecipe.getDbRatingScore();
        dbRatingPeople = dbRecipe.getDbRatingPeople();
        allergy = dbRecipe.getAllergy();
    }

    public static DbResponseDto createDbAllInfo(DbRecipe dbRecipe) {
        return new DbResponseDto(dbRecipe);
    }
}
