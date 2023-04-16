package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodInfo {
    @Id
    @GeneratedValue
    @Column(name = "food_info_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    //== 연관관계 편의 메서드==//
    public void changeFood(Food food) {
        this.food = food;
        food.getFoodInfos().add(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!

    public FoodInfo(Food food) {
        if (food != null) {
            changeFood(food);
        }
    }
}
