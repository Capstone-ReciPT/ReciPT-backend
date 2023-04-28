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
public class RecentSearch extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "recent_id")
    private Long recentId;

    private String recentSearchFoodName;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;

    //== 연관관계 편의 메서드 ==//
    public void changeUser(User user) {
        this.user = user;
        user.getRecentSearches().add(this);
    }

    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getRecentSearches().add(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.getRecentSearches().add(this);
    }

    //==생성 메서드==//

    public RecentSearch(User user, DbRecipe dbRecipe, String recentSearchFoodName) {
        changeUser(user);
        changeDbRecipe(dbRecipe);
        this.recentSearchFoodName = recentSearchFoodName;
    }

    public RecentSearch(User user, GptRecipe gptRecipe, String recentSearchFoodName) {
        changeUser(user);
        changeGptRecipe(gptRecipe);
        this.recentSearchFoodName = recentSearchFoodName;
    }

    public static RecentSearch createDbRecentSearch(User user, DbRecipe dbRecipe, String recentSearchFoodName) {
        return new RecentSearch(user, dbRecipe, recentSearchFoodName);
    }

    public static RecentSearch createGptRecentSearch(User user, GptRecipe gptRecipe, String recentSearchFoodName) {
        return new RecentSearch(user, gptRecipe, recentSearchFoodName);
    }
}
