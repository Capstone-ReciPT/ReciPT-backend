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
    private DbRecipe dbRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "register_id")
    private RegisterRecipe registerRecipe;

    //== 연관관계 편의 메서드 ==//
    public void changeUser(User user) {
        this.user = user;
        user.getHearts().add(this);
    }

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getHearts().add(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.getHearts().add(this);
    }

    //==생성 메서드==//

    public Heart(User user, DbRecipe dbRecipe) {
        changeUser(user);
        changeDbRecipe(dbRecipe);
    }

    public Heart(User user, GptRecipe gptRecipe) {
        changeUser(user);
        changeGptRecipe(gptRecipe);
    }
    

    public static Heart createDbHeart(User user, DbRecipe dbRecipe) {
        return new Heart(user, dbRecipe);
    }

    public static Heart createGptHeart(User user, GptRecipe gptRecipe) {
        return new Heart(user, gptRecipe);
    }
}
