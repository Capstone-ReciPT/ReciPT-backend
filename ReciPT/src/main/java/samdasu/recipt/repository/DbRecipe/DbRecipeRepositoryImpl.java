package samdasu.recipt.repository.DbRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.entity.QDbRecipe;

import java.util.List;

import static samdasu.recipt.entity.QDbRecipe.dbRecipe;

@Repository
@RequiredArgsConstructor
public class DbRecipeRepositoryImpl implements DbRecipeCustomRepository {
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
    public void subDbLikeCount(DbRecipe selectedDbRecipe) {
        queryFactory.update(dbRecipe)
                .set(dbRecipe.dbLikeCount, dbRecipe.dbLikeCount.subtract(1))
                .where(dbRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public List<DbRecipe> Top10DbRecipeLike(DbRecipe dbRecipe) {
        List<DbRecipe> top10Like = queryFactory
                .selectFrom(QDbRecipe.dbRecipe)
                .orderBy(QDbRecipe.dbRecipe.dbViewCount.desc())
                .limit(10)
                .fetch();
        return top10Like;
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
