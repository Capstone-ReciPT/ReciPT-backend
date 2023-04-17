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
public class Allergy {
    @Id
    @GeneratedValue
    @Column(name = "allergy_id")
    private Long allergyId;

    private String causedIngredient;

    @OneToMany(mappedBy = "allergy")
    private List<AllergyInfo> allergyInfo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    //== 연관관계 편의 메서드==//

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getAllergies().add(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.getAllergies().add(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!

    public Allergy(String causedIngredient, DbRecipe dbRecipe, GptRecipe gptRecipe) {
        this.causedIngredient = causedIngredient;
        if (dbRecipe != null) {
            changeDbRecipe(dbRecipe);
        }
        if (gptRecipe != null) {
            changeGptRecipe(gptRecipe);
        }
    }
}
