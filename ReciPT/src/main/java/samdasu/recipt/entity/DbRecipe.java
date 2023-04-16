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
public class DbRecipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    private String howToCook;
    private String thumbnailImage; //url
    private String recipeContext;
    private String recipeImage; //url

    @OneToMany(mappedBy = "dbRecipe")
    private List<Allergy> allergies;

    @OneToMany(mappedBy = "dbRecipe")
    private List<IngredientInfo> ingredientInfos;

    @OneToOne(mappedBy = "dbRecipe", fetch = LAZY)
    private Food food;

    @OneToOne(mappedBy = "dbRecipe", fetch = LAZY)
    private Review review;

    public void addDbReview(Review review) {
        this.review = review;
    }

    public void addFood(Food food) {
        this.food = food;
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public DbRecipe(String howToCook, String thumbnailImage, String recipeContext, String recipeImage) {
        this.howToCook = howToCook;
        this.thumbnailImage = thumbnailImage;
        this.recipeContext = recipeContext;
        this.recipeImage = recipeImage;
    }
}
