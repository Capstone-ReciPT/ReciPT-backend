package samdasu.recipt.domain.repository.Recipe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.domain.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;

import static samdasu.recipt.domain.entity.QRecipe.recipe;


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
    public List<Recipe> dynamicSearching(String searchingFoodName, Integer likeCond, Long viewCond) {
        return queryFactory
                .selectFrom(recipe)
                .where(searchByFoodNameContain(searchingFoodName), searchByLikeGoe(likeCond), searchByViewGoe(viewCond))
                .fetch();
    }

    private BooleanExpression searchByFoodNameContain(String searchingFoodName) {
        if (searchingFoodName == null) {
            return null;
        }
        return recipe.foodName.contains(searchingFoodName);
    }

    private BooleanExpression searchByLikeGoe(Integer likeCond) {
        if (likeCond == null) {
            return null;
        }
        return recipe.likeCount.goe(likeCond);
    }

    private BooleanExpression searchByViewGoe(Long viewCond) {
        if (viewCond == null) {
            return null;
        }
        return recipe.viewCount.goe(viewCond);
    }

    @Override
    public List<Recipe> Top10Like() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.likeCount.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10View() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.viewCount.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10RatingScore() {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.ratingPeople.gt(0))
                .orderBy(recipe.ratingScore.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10RecentRegister() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.createDate.desc())
                .limit(10)
                .fetch();
    }


    @Override
    public void resetViewCount(LocalDateTime yesterday) {
        queryFactory
                .update(recipe)
                .set(recipe.viewCount, 0L)
                .where(recipe.createDate.loe(yesterday))
                .execute();
    }


    @Override
    public List<String> RecommendByRandH2() {
        return queryFactory
                .select(recipe.foodName)
                .from(recipe)
                .orderBy(NumberExpression.random().asc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<String> RecommendByRandMySql() {
        return queryFactory
                .select(recipe.foodName)
                .from(recipe)
                .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                .limit(10)
                .fetch();
    }

}
