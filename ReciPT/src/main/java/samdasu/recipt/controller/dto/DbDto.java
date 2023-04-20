package samdasu.recipt.controller.dto;

import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Rating;

import java.util.List;
import java.util.stream.Collectors;

public class DbDto {
    private Long dbRecipeId;
    private String dbFoodName;
    private String dbIngredient;
    private String howToCook;
    private String thumbnailImage;
    private String dbContext;
    private String dbImage;
    private float dbRatingScore;
    private int dbRatingCount;
    private int dbLikeCount;

    private Allergy allergy;

    private Rating rating;

    private List<HeartDto> hearts;

    private List<ReviewDto> reviews;

    public DbDto(DbRecipe dbRecipe) {
        dbRecipeId = dbRecipe.getDbRecipeId();
        dbFoodName = dbRecipe.getDbFoodName();
        dbIngredient = dbRecipe.getDbIngredient();
        howToCook = dbRecipe.getHowToCook();
        thumbnailImage = dbRecipe.getThumbnailImage();
        dbContext = dbRecipe.getDbContext();
        dbImage = dbRecipe.getDbImage();
        dbRatingScore = dbRecipe.getDbRatingScore();
        dbRatingCount = dbRecipe.getDbRatingCount();
        dbLikeCount = dbRecipe.getDbLikeCount();
        allergy = dbRecipe.getAllergy();
        rating = dbRecipe.getRating();
        hearts = dbRecipe.getHearts().stream()
                .map(heart -> new HeartDto(heart))
                .collect(Collectors.toList());
        reviews = dbRecipe.getReview().stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
    }
}
