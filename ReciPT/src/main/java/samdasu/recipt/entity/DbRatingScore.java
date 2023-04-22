package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GptRatingScore {
    @Id
    @GeneratedValue
    @Column(name = "gptRating_id")
    private Long gptRatingId;

    private String gptRatingScore;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    //== 연관관계 편의 메서드 ==//
    public void setGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.getGptRatingScores().add(this);
    }

}
