package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchingAllRecipe {
    @Id
    @GeneratedValue
    @Column(name = "search_id")
    private Long searchId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private DbRecipe dbRecipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gpt_id")
    private GptRecipe gptRecipe;


    //== 연관관계 편의 메서드 ==//
    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getSearchingAllRecipes().add(this);
    }

    public void changeGptRecipe(GptRecipe gptRecipe) {
        this.gptRecipe = gptRecipe;
        gptRecipe.getSearchingAllRecipes().add(this);
    }

    //==생성 메서드==//

    public SearchingAllRecipe(DbRecipe dbRecipe, GptRecipe gptRecipe) {
        changeDbRecipe(dbRecipe);
        changeGptRecipe(gptRecipe);
    }

    public static SearchingAllRecipe createSearchingAllRecipe(DbRecipe dbRecipe, GptRecipe gptRecipe) {
        return new SearchingAllRecipe(dbRecipe, gptRecipe);
    }
}

 