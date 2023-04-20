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
public class DbRecipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long dbRecipeId;

    private String dbFoodName;
    private String dbIngredient;
    private String howToCook;
    private String thumbnailImage; //url형식
    private String dbContext;
    private String dbImage; //url형식

    private float dbRatingScore;

    private int dbRatingCount;

    private int dbLikeCount;

    @Embedded
    private Allergy allergy;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @OneToMany(mappedBy = "dbRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "dbRecipe")
    private List<Review> review = new ArrayList<>();

    //==비지니스 로직==//

  
}
