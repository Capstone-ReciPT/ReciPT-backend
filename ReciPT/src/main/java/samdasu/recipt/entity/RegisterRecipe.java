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

    @Column(nullable = false, length = 500)
    private String registerThumbnailImage;
    @Column(nullable = false)
    private String registerTitle;
    @Column(nullable = false)
    private String registerComment;
    @Column(nullable = false)
    private String registerIngredient;
    @Column(nullable = false)
    private String registerHowToCook;

    private Integer registerLikeCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "registerRecipe")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "registerRecipe")
    private List<DbRecipe> dbRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "registerRecipe")
    private List<GptRecipe> gptRecipes = new ArrayList<>();

    public RegisterRecipe(String registerThumbnailImage, String registerTitle, String registerComment, String registerIngredient, String registerHowToCook, Integer registerLikeCount) {
        this.registerThumbnailImage = registerThumbnailImage;
        this.registerTitle = registerTitle;
        this.registerComment = registerComment;
        this.registerIngredient = registerIngredient;
        this.registerHowToCook = registerHowToCook;
        this.registerLikeCount = registerLikeCount;
    }

    public static RegisterRecipe CreateRegisterRecipe(String registerThumbnailImage, String registerTitle, String registerComment, String registerIngredient, String registerHowToCook, Integer registerLikeCount) {
        return new RegisterRecipe(registerThumbnailImage, registerTitle, registerComment, registerIngredient, registerHowToCook, registerLikeCount);
    }
}
