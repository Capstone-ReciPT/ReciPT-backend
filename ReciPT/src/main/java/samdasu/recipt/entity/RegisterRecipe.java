package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRecipe extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "register_id")
    private Long registerId;
    @Column(nullable = false)
    private String foodName;
    @Lob
    @Column(length = 1000)
    private byte[] thumbnailImage;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String ingredient;

    @Column(nullable = false, length = 500)
    private String context;
    private Long viewCount; //조회 수
    private Integer likeCount;
    private Double ratingScore;
    private Integer ratingPeople;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private Gpt gpt;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @OneToMany(mappedBy = "registerRecipe")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "registerRecipe")
    private List<Heart> hearts = new ArrayList<>();


    @OneToMany(mappedBy = "registerRecipe")
    private List<ImageFile> imageFiles = new ArrayList<>();


    //== 연관관계 편의 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getRegisterRecipes().add(this);
    }

    public void addGpt(Gpt gpt) {
        this.gpt = gpt;
        gpt.getRegisterRecipes().add(this);
    }

    public void addImageFile(ImageFile image) {
        imageFiles.add(image);
        image.setRegisterRecipe(this);
    }

    //==생성 메서드==//
    public RegisterRecipe(String foodName, byte[] thumbnailImage, String title, String comment, String category, String ingredient, String context, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople) {
        this.foodName = foodName;
        this.thumbnailImage = thumbnailImage;
        this.title = title;
        this.comment = comment;
        this.category = category;
        this.ingredient = ingredient;
        this.context = context;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.ratingScore = ratingScore;
        this.ratingPeople = ratingPeople;
    }

    public static RegisterRecipe createRegisterRecipe(String foodName, byte[] thumbnailImage, String title, String comment, String category, String ingredient, String context, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople, User user, Gpt gpt, ImageFile... imageFiles) {
        RegisterRecipe registerRecipe = new RegisterRecipe(foodName, thumbnailImage, title, comment, category, ingredient, context, viewCount, likeCount, ratingScore, ratingPeople);
        registerRecipe.addUser(user);
        registerRecipe.addGpt(gpt);
        for (ImageFile imageFile : imageFiles) {
            registerRecipe.addImageFile(imageFile);
        }
        return registerRecipe;
    }

    //==비지니스 로직==//
    public void updateRating(Double inputRatingScore) {
        ratingScore += inputRatingScore;
        ratingPeople++;
    }

    /**
     * DB 평점 계산
     */
    public void calcRatingScore(RegisterRecipe recipe) {
        recipe.ratingScore = Math.round(recipe.getRatingScore() / recipe.getRatingPeople() * 100) / 100.0;
    }

}
