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
public class Recipe extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(nullable = false)
    private String foodName;
    @Column(nullable = false)
    private String ingredient;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false, length = 500)
    private String thumbnailImage; //url형식
    @Column(nullable = false, length = 500)
    private String context;
    @Column(nullable = false, length = 500)
    private String image; //url형식
    private Long viewCount; //조회 수
    private Integer likeCount; // 레시피 좋아요
    private Double ratingScore;
    private Integer ratingPeople;

    @OneToMany(mappedBy = "recipe")
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToOne(mappedBy = "recipe")
    private RegisterRecipe registerRecipes;


    //==생성 메서드==/
    public Recipe(String foodName, String ingredient, String category, String thumbnailImage, String context, String image, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople) {
        this.foodName = foodName;
        this.ingredient = ingredient;
        this.category = category;
        this.thumbnailImage = thumbnailImage;
        this.context = context;
        this.image = image;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.ratingScore = ratingScore;
        this.ratingPeople = ratingPeople;
    }


    public static Recipe createRecipe(String foodName, String ingredient, String category, String thumbnailImage, String context, String image, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople) {
        return new Recipe(foodName, ingredient, category, thumbnailImage, context, image, viewCount, likeCount, ratingScore, ratingPeople);
    }

    //==비지니스 로직==//
    public void updateRating(Double inputRatingScore) {
        ratingScore += inputRatingScore;
        ratingPeople++;
    }

    /**
     * DB 평점 계산
     */
    public void calcRatingScore(Recipe recipe) {
        recipe.ratingScore = Math.round(recipe.getRatingScore() / recipe.getRatingPeople() * 100) / 100.0;
    }
}
