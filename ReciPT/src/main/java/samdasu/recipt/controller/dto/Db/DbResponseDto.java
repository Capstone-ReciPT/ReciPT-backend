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
import java.util.stream.Collectors;

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
    private Integer dbViewCount;
    private Double dbRatingResult;
    private Allergy allergy;
    private List<DbHeartDto> hearts;
    private List<ReviewRequestDto> reviews;

    public DbResponseDto(DbRecipe dbRecipe) {
        dbRecipeId = dbRecipe.getDbRecipeId();
        dbFoodName = dbRecipe.getDbFoodName();
        dbIngredient = dbRecipe.getDbIngredient();
        howToCook = dbRecipe.getHowToCook();
        thumbnailImage = dbRecipe.getThumbnailImage();
        dbContext = dbRecipe.getDbContext();
        dbImage = dbRecipe.getDbImage();
        dbLikeCount = dbRecipe.getDbLikeCount();
        dbViewCount = dbRecipe.getDbViewCount();
        allergy = dbRecipe.getAllergy();
        hearts = dbRecipe.getHearts().stream()
                .map(heart -> new DbHeartDto(heart))
                .collect(Collectors.toList());
        reviews = dbRecipe.getReview().stream()
                .map(review -> new ReviewRequestDto(review))
                .collect(Collectors.toList());
    }

    public static DbResponseDto createDbResponseDto(DbRecipe dbRecipe) {
        return new DbResponseDto(dbRecipe);
    }
}
