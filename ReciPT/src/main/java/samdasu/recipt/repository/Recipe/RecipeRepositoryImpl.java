package samdasu.recipt.repository.Recipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.Recipe;

import java.util.List;

import static samdasu.recipt.entity.QRecipe.recipe;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addRecipeLikeCount(Recipe selectedRecipe) {
        queryFactory.update(recipe)
                .set(recipe.likeCount, recipe.likeCount.add(1))
                .where(recipe.eq(selectedRecipe))
                .execute();
    }

    @Override
    public void subRecipeLikeCount(Recipe selectedRecipe) {
        queryFactory.update(recipe)
                .set(recipe.likeCount, recipe.likeCount.subtract(1))
                .where(recipe.eq(selectedRecipe))
                .execute();
    }

    @Override
    public void addRecipeViewCount(Recipe selectedRecipe) {
        queryFactory.update(recipe)
                .set(recipe.viewCount, recipe.viewCount.add(1))
                .where(recipe.eq(selectedRecipe))
                .execute();
    }

    @Override
    public List<Recipe> findRecipeByContain(String searchingFoodName) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.foodName.contains(searchingFoodName))
                .fetch();
    }

    /**
     * 좋아요 사용자 입력 값 이상 조회
     */
    @Override
    public List<Recipe> SearchingRecipeLikeByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.likeCount.goe(inputNum))
                .fetch();
    }

    /**
     * 조회수 사용자 입력 값 이상 조회
     */
    @Override
    public List<Recipe> SearchingRecipeViewCountByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.viewCount.goe(inputNum))
                .fetch();
    }

//    @Override
//    public List<DbRecipe> Top10DbRecipeView() {
//        return queryFactory
//                .selectFrom(dbRecipe)
//                .orderBy(dbRecipe.dbViewCount.desc())
//                .limit(10)
//                .fetch();
//    }
//
//    @Override
//    public List<DbRecipe> Top10DbRecipeLike() {
//        return queryFactory
//                .selectFrom(dbRecipe)
//                .orderBy(dbRecipe.dbLikeCount.desc())
//                .limit(10)
//                .fetch();
//    }
//
//    /**
//     * Db 좋아요 1위 조회
//     */
//    @Override
//    public DbRecipe Top1DbRecipeLike() {
//        return queryFactory
//                .selectFrom(dbRecipe)
//                .orderBy(dbRecipe.dbLikeCount.desc())
//                .limit(1)
//                .fetchOne();
//    }
//
//    /**
//     * Db 조회 수 1위 조회
//     */
//    @Override
//    public DbRecipe Top1DbRecipeViewCount() {
//        return queryFactory
//                .selectFrom(dbRecipe)
//                .orderBy(dbRecipe.dbViewCount.desc())
//                .limit(1)
//                .fetchOne();
//    }
//
//    /**
//     * DB 평점 1위
//     */
//    @Override
//    public DbRecipe Top1DbRecipeRatingScore() {
//        return queryFactory
//                .selectFrom(dbRecipe)
//                .orderBy(dbRecipe.dbRatingScore.desc())
//                .limit(1)
//                .fetchOne();
//    }
//
//    @Override
//    public List<Tuple> Top10AllRecipeLike() {
//        List<Tuple> fetch = queryFactory
//                .select(recentSearch.gptRecipe, dbRecipe)
//                .from(recentSearch)
//                .join(recentSearch.dbRecipe, dbRecipe)
//                .fetch();
//        return fetch;
//    }
}
