package samdasu.recipt.repository.DbRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.GptRecipe;

import javax.persistence.EntityManager;
import java.util.List;

import static samdasu.recipt.entity.QDbRecipe.dbRecipe;
import static samdasu.recipt.entity.QGptRecipe.gptRecipe;

@Repository
@RequiredArgsConstructor
public class DBRecipeRepositoryImpl implements DBRecipeCustomRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<DbRecipe> findAllByQuerydsl() {
        return queryFactory
                .selectFrom(dbRecipe)
                .fetch();
    }

    @Override
    public void addDbLikeCount(DbRecipe selectedDbRecipe) {
        queryFactory.update(dbRecipe)
                .set(dbRecipe.dbLikeCount, dbRecipe.dbLikeCount.add(1))
                .where(dbRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public void addGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.add(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    @Override
    public void subDbLikeCount(DbRecipe selectedDbRecipe) {
        queryFactory.update(dbRecipe)
                .set(dbRecipe.dbLikeCount, dbRecipe.dbLikeCount.subtract(1))
                .where(dbRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public void subGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.subtract(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    /**
     * DB 레시피 평점 평균 구하기
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
