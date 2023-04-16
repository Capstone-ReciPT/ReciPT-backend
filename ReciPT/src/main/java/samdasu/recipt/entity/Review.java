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
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String comment;

    private int review_grade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "review")
    private List<Like> likes;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private ImageFile imageFile;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    //== 연관관계 편의 메서드==//
    public void changeUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    public void changeImageFile(ImageFile imageFile) {
        this.imageFile = imageFile;
        imageFile.addImageFile(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.addGptReview(this);
    }

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.addDbReview(this);
    }


    //==생성 메서드==// 앞으로 생성하는 지점 변경 시에는 여기만 수정하면 됨!
    public Review(String comment, int review_grade, User user, List<Like> likes, ImageFile imageFile, GptRecipe gptRecipe, DbRecipe dbRecipe) {
        this.comment = comment;
        this.review_grade = review_grade;
        this.user = user;
        this.likes = likes;
        if (imageFile != null) {
            changeImageFile(imageFile);
        }
        if (gptRecipe != null) {
            changeGptRecipe(gptRecipe);
        }
        if (dbRecipe != null) {
            changeDbRecipe(dbRecipe);
        }
    }
}
