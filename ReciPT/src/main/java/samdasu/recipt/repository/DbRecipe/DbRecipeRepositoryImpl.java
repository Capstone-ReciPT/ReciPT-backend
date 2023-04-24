package samdasu.recipt.repository.DbRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.Heart;
import samdasu.recipt.entity.QDbRecipe;

import javax.persistence.EntityManager;
import java.util.List;

import static samdasu.recipt.entity.QDbRecipe.dbRecipe;

@Repository
@RequiredArgsConstructor
public class DbRecipeRepositoryImpl implements DbRecipeCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addDbLikeCount(DbRecipe selectedDbRecipe) {
        queryFactory.update(dbRecipe)
                .set(dbRecipe.dbLikeCount, dbRecipe.dbLikeCount.add(1))
                .where(dbRecipe.eq(selectedDbRecipe))
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
    public List<DbRecipe> Top10DbRecipeView(DbRecipe dbRecipe) {
        List<DbRecipe> top10View = queryFactory
                .selectFrom(QDbRecipe.dbRecipe)
                .orderBy(QDbRecipe.dbRecipe.dbViewCount.desc())
                .limit(10)
                .fetch();
        return top10View;
    }

    @Override
    public List<DbRecipe> Top10DbRecipeLike(DbRecipe dbRecipe) {
        List<DbRecipe> top10Like = queryFactory
                .selectFrom(QDbRecipe.dbRecipe)
                .orderBy(QDbRecipe.dbRecipe.dbLikeCount.desc())
                .limit(10)
                .fetch();
        return top10Like;
    }

    private final EntityManager em;

    @Override
    public List<Heart> Top10AllRecipeLike() {
        return em.createQuery(
                        "select h.dbRecipe.dbFoodName, h.gptRecipe.gptFoodName from Heart h" +
                                " join h.dbRecipe db" +
                                " join h.gptRecipe gpt", Heart.class)
                .getResultList();
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
