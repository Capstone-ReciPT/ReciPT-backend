package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long likeId;

    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    //== 연관관계 편의 메서드==//
    public void changeUser(User user) {
        this.user = user;
        user.getLikes().add(this);
    }

    public void changeReview(Review review) {
        this.review = review;
        review.getLikes().add(this);
    }

    public void changeFood(Food food) {
        this.food = food;
        food.getLikes().add(this);
    }

    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public Like(int count, User user, Review review, Food food) {
        this.count = count;
        if (user != null) {
            changeUser(user);
        }
        if (review != null) {
            changeReview(review);
        }
        if (food != null) {
            changeFood(food);
        }
    }
}
