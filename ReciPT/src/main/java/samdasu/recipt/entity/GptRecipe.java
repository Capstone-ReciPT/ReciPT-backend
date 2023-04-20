package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GptRecipe {
    @Id
    @GeneratedValue
    @Column(name = "gpt_id")
    private Long gptRecipeId;
    private String gptFoodName;
    private String gptIngredient;
    private String gptHowToCook;
    private String gptTip;
    private float gptRatingScore;

    private int gptRatingCount;

    private int gptLikeCount;


    @Embedded
    private Allergy allergy;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @OneToMany(mappedBy = "gptRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToOne(mappedBy = "gptRecipe")
    private Review review;

    public void setReview(Review review) { //일대일 연관관계 편의 메서드 때문에 set 열어둠
        this.review = review;
    }

    public GptRecipe(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, float gptRatingScore, int gptRatingCount, int gptLikeCount) {
        this.gptFoodName = gptFoodName;
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
        this.gptRatingScore = gptRatingScore;
        this.gptRatingCount = gptRatingCount;
        this.gptLikeCount = gptLikeCount;
        this.allergy = allergy;
        this.rating = rating;
        this.hearts = hearts;
        this.review = review;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public static GptRecipe gptRecipe(String gptFoodName, String gptIngredient, String gptHowToCook, String gptTip, float gptRatingScore, int gptRatingCount, int gptLikeCount) {
        return new GptRecipe(gptFoodName, gptIngredient, gptHowToCook, gptTip, gptRatingScore, gptRatingCount, gptLikeCount);
    }
}
