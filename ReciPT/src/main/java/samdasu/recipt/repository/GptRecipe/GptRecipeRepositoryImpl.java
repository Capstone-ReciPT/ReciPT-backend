package samdasu.recipt.repository.GptRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.QGptRecipe;

import java.util.List;

import static samdasu.recipt.entity.QGptRecipe.gptRecipe;

@Repository
@RequiredArgsConstructor
public class GptRecipeRepositoryImpl implements GptRecipeCustomRepository {
    private final JPAQueryFactory queryFactory;
    
    @Override
    public void addGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.add(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    @Override
    public void subGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.subtract(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    @Override
    public void addGptViewCount(GptRecipe selectedDbRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptViewCount, gptRecipe.gptViewCount.add(1))
                .where(gptRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public List<GptRecipe> findGptRecipeByContain(String searchingFoodName) {
        return queryFactory
                .selectFrom(gptRecipe)
                .where(gptRecipe.gptFoodName.contains(searchingFoodName))
                .fetch();
    }

    @Override
    public List<GptRecipe> Top10GptRecipeView() {
        List<GptRecipe> top10View = queryFactory
                .selectFrom(QGptRecipe.gptRecipe)
                .orderBy(QGptRecipe.gptRecipe.gptViewCount.desc())
                .limit(10)
                .fetch();
        return top10View;
    }

    @Override
    public List<GptRecipe> Top10GptRecipeLike() {
        List<GptRecipe> top10Like = queryFactory
                .selectFrom(QGptRecipe.gptRecipe)
                .orderBy(QGptRecipe.gptRecipe.gptLikeCount.desc())
                .limit(10)
                .fetch();
        return top10Like;
    }
}
