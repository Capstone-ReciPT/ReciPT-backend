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
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long foodId;

    private String foodName;

    @OneToMany(mappedBy = "food")
    private List<FoodInfo> foodInfos;

    @OneToMany(mappedBy = "food")
    private List<Like> likes;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    //== 연관관계 편의 메서드==//

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.addFood(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!

    public Food(String foodName, DbRecipe dbRecipe) {
        this.foodName = foodName;
        if (dbRecipe != null) {
            changeDbRecipe(dbRecipe);
        }
    }
}
