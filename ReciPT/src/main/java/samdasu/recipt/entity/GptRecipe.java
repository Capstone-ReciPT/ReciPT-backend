package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GptRecipe {
    @Id
    @GeneratedValue
    @Column(name = "gpt_id")
    private Long id;

    private String gptIngredient;
    private String gptHowToCook;
    private String gptTip;

    @OneToMany(mappedBy = "gptRecipe")
    private List<Allergy> allergies;

    @OneToOne(mappedBy = "gptRecipe", fetch = LAZY)
    private Review review;

    public void addGptReview(Review review) {
        this.review = review;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public GptRecipe(String gptIngredient, String gptHowToCook, String gptTip) {
        this.gptIngredient = gptIngredient;
        this.gptHowToCook = gptHowToCook;
        this.gptTip = gptTip;
    }
}
