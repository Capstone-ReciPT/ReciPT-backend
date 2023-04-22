package samdasu.recipt.controller.dto;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.controller.dto.Heart.DbHeartDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DbDto {
    private Long dbRecipeId;
    private String dbFoodName;
    private String dbIngredient;
    private String howToCook;
    private String thumbnailImage;
    private String dbContext;
    private String dbImage;

    private Integer dbLikeCount;
    private Integer dbViewCount;

    private Allergy allergy;

    private List<DbHeartDto> hearts;

    private List<ReviewRequestDto> reviews;

    public DbDto(DbRecipe dbRecipe) {
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
}
