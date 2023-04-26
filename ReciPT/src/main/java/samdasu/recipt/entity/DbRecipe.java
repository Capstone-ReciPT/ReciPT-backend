package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DbRecipe extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long dbRecipeId;

    @Column(nullable = false)
    private String dbFoodName;
    @Column(nullable = false)
    private String dbIngredient;
    @Column(nullable = false)
    private String howToCook;
    @Column(nullable = false, length = 500)
    private String thumbnailImage; //url형식
    @Column(nullable = false, length = 500)
    private String dbContext;
    @Column(nullable = false, length = 500)
    private String dbImage; //url형식

    private Long dbViewCount; //조회 수
    private Integer dbLikeCount; // 레시피 좋아요

    private Double dbRatingScore;
    private Integer dbRatingPeople;
    @Embedded
    private Allergy allergy;

    @OneToMany(mappedBy = "dbRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "dbRecipe")
    private List<Review> review = new ArrayList<>();


    //==생성 메서드==/
    public DbRecipe(String dbFoodName, String dbIngredient, String howToCook, String thumbnailImage, String dbContext, String dbImage, Integer dbLikeCount, Long dbViewCount, Double dbRatingScore, Integer dbRatingPeople, Allergy allergy) {
        this.dbFoodName = dbFoodName;
        this.dbIngredient = dbIngredient;
        this.howToCook = howToCook;
        this.thumbnailImage = thumbnailImage;
        this.dbContext = dbContext;
        this.dbImage = dbImage;
        this.dbLikeCount = dbLikeCount;
        this.dbViewCount = dbViewCount;
        this.dbRatingScore = dbRatingScore;
        this.dbRatingPeople = dbRatingPeople;
        this.allergy = allergy;
    }

    public static DbRecipe createDbRecipe(String dbFoodName, String dbIngredient, String howToCook, String thumbnailImage, String dbContext, String dbImage, Integer dbLikeCount, Long dbViewCount, Double dbRatingScore, Integer dbRatingPeople, Allergy allergy) {
        return new DbRecipe(dbFoodName, dbIngredient, howToCook, thumbnailImage, dbContext, dbImage, dbLikeCount, dbViewCount, dbRatingScore, dbRatingPeople, allergy);
    }

    //==비지니스 로직==//
    public void updateRating(Double ratingScore) {
        dbRatingScore += ratingScore;
        dbRatingPeople++;
    }

    /**
     * DB 평점 계산
     */
    public Double calcDbRatingScore(DbRecipe dbRecipe) {
        return Math.round(dbRecipe.getDbRatingScore() / dbRecipe.getDbRatingPeople() * 100) / 100.0;
    }
}
