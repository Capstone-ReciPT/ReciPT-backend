package samdasu.recipt.repository.GptRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.QGptRecipe;

import java.util.List;

import static samdasu.recipt.entity.QDbRecipe.dbRecipe;
import static samdasu.recipt.entity.QGptRecipe.gptRecipe;

@Repository
@RequiredArgsConstructor
public class GptRecipeRepositoryImpl implements GptRecipeCustomRepository {
    private final JPAQueryFactory queryFactory;

    public List<DbRecipe> findAllByQuerydsl() {
        return queryFactory
                .selectFrom(dbRecipe)
                .fetch();
    }

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
    public List<GptRecipe> Top10GptRecipeLike(GptRecipe gptRecipe) {
        List<GptRecipe> top10Like = queryFactory
                .selectFrom(QGptRecipe.gptRecipe)
                .orderBy(QGptRecipe.gptRecipe.gptViewCount.desc())
                .limit(10)
                .fetch();
        return top10Like;
    }

    /**
     * Gpt 레시피 평점 평균 구하기
     * -
     */
//    public List<DbRecipe> searchAverageRatingScore() {
//        List<Tuple> result = queryFactory
//                .select(dbRecipe.dbFoodName)
//                .from(dbRecipe)
//                .groupBy(dbRecipe.dbRecipeId)
//                .fetch();
//        return null;
//    }
}
