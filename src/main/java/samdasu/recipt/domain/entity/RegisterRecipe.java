package samdasu.recipt.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.converters.StringConverter;
import samdasu.recipt.domain.common.BaseTimeEntity;
import samdasu.recipt.domain.entity.converter.StringListConverter;

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
    private String thumbnailImage;
    @Convert(converter = StringListConverter.class)
    @Column(length = 1000)
    private List<String> image = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private Gpt gpt;

    @OneToMany(mappedBy = "registerRecipe")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "registerRecipe")
    private List<Heart> hearts = new ArrayList<>();

    //== 연관관계 편의 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getRegisterRecipes().add(this);
    }

    public void addGpt(Gpt gpt) {
        this.gpt = gpt;
        gpt.getRegisterRecipes().add(this);
    }


    //==생성 메서드==//
    public RegisterRecipe(String foodName, String comment, String category, String ingredient, String context, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople, String thumbnailImage, List<String> image) {
        this.foodName = foodName;
        this.comment = comment;
        this.category = category;
        this.ingredient = ingredient;
        this.context = context;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.ratingScore = ratingScore;
        this.ratingPeople = ratingPeople;
        this.thumbnailImage = thumbnailImage;
        this.image = image;
    }

    public static RegisterRecipe createRegisterRecipe(String foodName, String comment, String category, String ingredient, String context, Long viewCount, Integer likeCount, Double ratingScore, Integer ratingPeople, String thumbnailImage, List<String> image, User user, Gpt gpt) {
        RegisterRecipe registerRecipe = new RegisterRecipe(foodName, comment, category, ingredient, context, viewCount, likeCount, ratingScore, ratingPeople, thumbnailImage, image);
        registerRecipe.addUser(user);
        registerRecipe.addGpt(gpt);
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
