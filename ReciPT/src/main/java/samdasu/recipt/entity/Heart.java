package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "heart")
public class DbHeart {
    @Id
    @GeneratedValue
    @Column(name = "heart_id")
    private Long heartId;

    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    //== 연관관계 편의 메서드 ==//


    public void changeUser(User user) {
        this.user = user;
        user.getDbHearts().add(this);
    }

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getDbHearts().add(this);
    }

}
