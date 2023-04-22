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

    private String dbFoodName;
    private String dbIngredient;
    private String howToCook;
    private String thumbnailImage; //url형식
    private String dbContext;
    private String dbImage; //url형식
    private Integer dbViewCount; //조회 수
    private Integer dbLikeCount; // 레시피 좋아요
    @Embedded
    private Allergy allergy;


    @OneToMany(mappedBy = "dbRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "dbRecipe")
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "dbRecipe")
    private List<DbRatingScore> dbRatingScores = new ArrayList<>();

    //==비지니스 로직==//


    //==생성 메서드==/
    public DbRecipe(String dbFoodName, String dbIngredient, String howToCook, String thumbnailImage, String dbContext, String dbImage, Integer dbLikeCount, Integer dbViewCount, Allergy allergy) {
        this.dbFoodName = dbFoodName;
        this.dbIngredient = dbIngredient;
        this.howToCook = howToCook;
        this.thumbnailImage = thumbnailImage;
        this.dbContext = dbContext;
        this.dbImage = dbImage;
        this.dbLikeCount = dbLikeCount;
        this.dbViewCount = dbViewCount;
        this.allergy = allergy;
    }

    public static DbRecipe createDbRecipe(String dbFoodName, String dbIngredient, String howToCook, String thumbnailImage, String dbContext, String dbImage, Integer dbLikeCount, Integer dbViewCount, Allergy allergy) {
        return new DbRecipe(dbFoodName, dbIngredient, howToCook, thumbnailImage, dbContext, dbImage, dbLikeCount, dbViewCount, allergy);
    }

}
