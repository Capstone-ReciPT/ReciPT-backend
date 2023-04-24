package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.global.BaseEntity;

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
    private String title;
    @Column(nullable = false)
    private String comment;
    private Integer viewCount; //조회 수
    private Integer likeCount; //리뷰 좋아요 개수
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

    public void addImageFile(ImageFile imageFile) {
        imageFiles.add(imageFile);
        imageFile.setReview(this);
    }

    public Review(String title, String comment, Integer viewCount, Integer likeCount, User user, GptRecipe gptRecipe, DbRecipe dbRecipe, ImageFile... imageFiles) {
        this.title = title;
        this.comment = comment;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        changeUser(user);
        for (ImageFile imageFile : imageFiles) {
            addImageFile(imageFile);
        }
        changeGptRecipe(gptRecipe);
        changeDbRecipe(dbRecipe);
    }

    public static Review createReview(String title, String comment, Integer viewCount, Integer likeCount, User user, GptRecipe gptRecipe, DbRecipe dbRecipe) {
        return new Review(title, comment, viewCount, likeCount, user, gptRecipe, dbRecipe);
    }

    public static Review createReview(ReviewRequestDto reviewRequestDto) {
        return new Review();
    }

    //==비지니스 로직==//
    public void addViewCount(Review review) {
        review.viewCount++;
    }

    public void addReviewLike(Review review) {
        review.likeCount++;
    }

    public void subReviewLike(Review review) {
        review.likeCount--;
    }

    public void updateReviewInfo(ReviewRequestDto reviewRequestDto) {
        this.title = reviewRequestDto.getTitle();
        this.comment = reviewRequestDto.getComment();
//        this.imageFiles = reviewRequestDto.getImageFiles();
    }

}
