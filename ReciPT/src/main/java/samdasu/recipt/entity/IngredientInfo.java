package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientInfo {
    @Id
    @GeneratedValue
    @Column(name = "ingredient_info_id")
    private Long ingredientInfoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    //== 연관관계 편의 메서드==//

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getIngredientInfos().add(this);
    }

    public void changeIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        ingredient.getIngredientInfos().add(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public IngredientInfo(DbRecipe dbRecipe, Ingredient ingredient) {
        if (dbRecipe != null) {
            changeDbRecipe(dbRecipe);
        }
        if (ingredient != null) {
            changeIngredient(ingredient);
        }
    }
}
