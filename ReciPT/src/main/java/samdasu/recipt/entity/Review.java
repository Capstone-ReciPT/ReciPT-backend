package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;

    private String title;
    private String comment;
    private Long viewCount; //조회 수

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "reviews", cascade = CascadeType.ALL)
    private List<ImageFile> imageFiles = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    //== 연관관계 편의 메서드 ==//
    public void changeUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.setReview(this);
    }

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getReview().add(this);
    }


    //==비지니스 로직==//

    public void addViewCount() {
        this.viewCount += 1;
    }
    

}
