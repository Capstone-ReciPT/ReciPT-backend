package samdasu.recipt.repository.DbRecipe;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.DbRecipe;

import java.util.List;

import static samdasu.recipt.entity.QDbRecipe.dbRecipe;
import static samdasu.recipt.entity.QRecentSearch.recentSearch;

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
    public void addDbViewCount(DbRecipe selectedDbRecipe) {
        queryFactory.update(dbRecipe)
                .set(dbRecipe.dbViewCount, dbRecipe.dbViewCount.add(1))
                .where(dbRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public List<DbRecipe> findDbRecipeByContain(String searchingFoodName) {
        return queryFactory
                .selectFrom(dbRecipe)
                .where(dbRecipe.dbFoodName.contains(searchingFoodName))
                .fetch();
    }

    @Override
    public List<DbRecipe> Top10DbRecipeView() {
        return queryFactory
                .selectFrom(dbRecipe)
                .orderBy(dbRecipe.dbViewCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<DbRecipe> Top10DbRecipeLike() {
        return queryFactory
                .selectFrom(dbRecipe)
                .orderBy(dbRecipe.dbLikeCount.desc())
                .limit(10)
                .fetch();
    }

    /**
     * Db 좋아요 1위 조회
     */
    @Override
    public DbRecipe Top1DbRecipeLike() {
        return queryFactory
                .selectFrom(dbRecipe)
                .orderBy(dbRecipe.dbLikeCount.desc())
                .limit(1)
                .fetchOne();
    }

    /**
     * Db 조회 수 1위 조회
     */
    @Override
    public DbRecipe Top1DbRecipeViewCount() {
        return queryFactory
                .selectFrom(dbRecipe)
                .orderBy(dbRecipe.dbViewCount.desc())
                .limit(1)
                .fetchOne();
    }

    /**
     * DB 평점 1위
     */
    @Override
    public DbRecipe Top1DbRecipeRatingScore() {
        return queryFactory
                .selectFrom(dbRecipe)
                .orderBy(dbRecipe.dbRatingScore.desc())
                .limit(1)
                .fetchOne();
    }


    /**
     * 좋아요 사용자 입력 값 이상 조회
     */
    @Override
    public List<DbRecipe> SearchingDbRecipeLikeByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(dbRecipe)
                .where(dbRecipe.dbLikeCount.goe(inputNum))
                .fetch();
    }

    /**
     * 조회수 사용자 입력 값 이상 조회
     */
    @Override
    public List<DbRecipe> SearchingDbRecipeViewCountByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(dbRecipe)
                .where(dbRecipe.dbViewCount.goe(inputNum))
                .fetch();
    }

    @Override
    public List<Tuple> Top10AllRecipeLike() {
        List<Tuple> fetch = queryFactory
                .select(recentSearch.gptRecipe, dbRecipe)
                .from(recentSearch)
                .join(recentSearch.dbRecipe, dbRecipe)
                .fetch();
        return fetch;
    }

}
