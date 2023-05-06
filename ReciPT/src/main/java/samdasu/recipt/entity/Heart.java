package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "heart")
public class Heart extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "heart_id")
    private Long heartId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "register_id")
    private RegisterRecipe registerRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    //== 연관관계 편의 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getHearts().add(this);
    }

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
        recipe.getHearts().add(this);
    }

    public void addRegisterRecipe(RegisterRecipe registerRecipe) {
        this.registerRecipe = registerRecipe;
        registerRecipe.getHearts().add(this);
    }

    public void addReview(Review review) {
        this.review = review;
        review.getHearts().add(this);
    }

    //==생성 메서드==//
    public Heart(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }

    public static Heart createRecipeHeart(User user, Recipe recipe) {
        Heart heart = new Heart();
        heart.addUser(user);
        heart.addRecipe(recipe);
        return heart;
    }

    public Heart(User user, RegisterRecipe registerRecipe) {
        this.user = user;
        this.registerRecipe = registerRecipe;
    }

    public static Heart createRegiterRecipeHeart(User user, RegisterRecipe registerRecipe) {
        Heart heart = new Heart();
        heart.addUser(user);
        heart.addRegisterRecipe(registerRecipe);
        return heart;
    }

    public Heart(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public static Heart createReviewHeart(User user, Review review) {
        Heart heart = new Heart();
        heart.addUser(user);
        heart.addReview(review);
        return heart;
    }
}
