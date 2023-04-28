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
public class GptRecipe extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "gpt_id")
    private Long gptRecipeId;
    @Column(nullable = false)
    private String gptFoodName;
    @Column(nullable = false)
    private String gptIngredient;
    @Column(nullable = false)
    private String gptHowToCook;
    private String gptTip;
    private Long gptViewCount; //조회 수
    private Integer gptLikeCount; //레시피 좋아요
    private Double gptRatingScore;
    private Integer gptRatingPeople;
    @Embedded
    private Allergy allergy;

    @OneToMany(mappedBy = "gptRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "gptRecipe")
    private List<RecentSearch> recentSearches = new ArrayList<>();

    @OneToMany(mappedBy = "gptRecipe")
    private List<Review> review = new ArrayList<>();


    public GptRecipe(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Integer gptLikeCount, Long gptViewCount, Double gptRatingScore, Integer gptRatingPeople, Allergy allergy) {
        this.gptFoodName = gptFoodName;
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
        this.gptLikeCount = gptLikeCount;
        this.gptViewCount = gptViewCount;
        this.gptRatingScore = gptRatingScore;
        this.gptRatingPeople = gptRatingPeople;
        this.allergy = allergy;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public static GptRecipe createGptRecipe(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, Integer gptLikeCount, Long gptViewCount, Double gptRatingScore, Integer gptRatingPeople, Allergy allergy) {
        return new GptRecipe(gptFoodName, gptIngredient, gptHowToCook, gptTip, gptLikeCount, gptViewCount, gptRatingScore, gptRatingPeople, allergy);
    }

    //==비지니스 로직==//
    public void updateRating(Double ratingScore) {
        gptRatingScore += ratingScore;
        gptRatingPeople++;
    }

    /**
     * Gpt 평점 계산
     */
    public Double calcGptRatingScore(GptRecipe gptRecipe) {
        return Math.round(gptRecipe.getGptRatingScore() / gptRecipe.getGptRatingPeople() * 100) / 100.0;
    }
}
