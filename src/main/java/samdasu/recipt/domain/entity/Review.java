package samdasu.recipt.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.domain.common.BaseEntity;
import samdasu.recipt.domain.controller.dto.Review.ReviewUpdateRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;
    @Column(nullable = false)
    private String comment;
    private Integer likeCount;
    private Double ratingScore;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "register_id")
    private RegisterRecipe registerRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(mappedBy = "review")
    private List<Heart> hearts = new ArrayList<>();

    //== 연관관계 편의 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    public void addRegisterRecipe(RegisterRecipe registerRecipe) {
        this.registerRecipe = registerRecipe;
        registerRecipe.getReviews().add(this);
    }

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
        recipe.getReviews().add(this);
    }
    //==생성 메서드==//


    public Review(String comment, Integer likeCount, Double ratingScore) {
        this.comment = comment;
        this.likeCount = likeCount;
        this.ratingScore = ratingScore;

    }

    public static Review createRecipeReview(String comment, Integer likeCount, Double ratingScore, User user, Recipe recipe) {
        Review review = new Review(comment, likeCount, ratingScore);
        review.addUser(user);
        review.addRecipe(recipe);
        return review;
    }

    public static Review createRegisterReview(String comment, Integer likeCount, Double ratingScore, User user, RegisterRecipe registerRecipe) {
        Review review = new Review(comment, likeCount, ratingScore);
        review.addUser(user);
        review.addRegisterRecipe(registerRecipe);
        return review;
    }

    //==비지니스 로직==//

    public void updateReviewInfo(ReviewUpdateRequestDto reviewUpdateRequestDto) {
        this.comment = reviewUpdateRequestDto.getComment();
    }

}
