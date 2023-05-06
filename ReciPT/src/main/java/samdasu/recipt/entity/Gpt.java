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
public class Gpt extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "gpt_id")
    private Long gptId;

    @Column(nullable = false)
    private String foodName;
    @Column(nullable = false)
    private String ingredient;
    @Column(nullable = false, length = 1000)
    private String context;

    @OneToMany(mappedBy = "gpt")
    private List<RegisterRecipe> registerRecipes = new ArrayList<>();


    //==생성 메서드==/
    public Gpt(String foodName, String ingredient, String context) {
        this.foodName = foodName;
        this.ingredient = ingredient;
        this.context = context;
    }

    public static Gpt createGpt(String foodName, String ingredient, String context) {
        return new Gpt(foodName, ingredient, context);
    }


    //==비지니스 로직==//
//    public void updateRating(Double ratingScore) {
//        dbRatingScore += ratingScore;
//        dbRatingPeople++;
//    }
//
//    /**
//     * DB 평점 계산
//     */
//    public Double calcDbRatingScore(DbRecipe dbRecipe) {
//        return Math.round(dbRecipe.getDbRatingScore() / dbRecipe.getDbRatingPeople() * 100) / 100.0;
//    }
}
