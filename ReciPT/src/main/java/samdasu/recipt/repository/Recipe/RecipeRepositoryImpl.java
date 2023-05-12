package samdasu.recipt.repository.Recipe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;

import static samdasu.recipt.entity.QRecipe.recipe;
import static samdasu.recipt.entity.QRegisterRecipe.registerRecipe;
import static samdasu.recipt.entity.QUser.user;

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
    public List<Recipe> dynamicSearching(int likeCond, int viewCond, String searchingFoodName) {
        return queryFactory
                .selectFrom(recipe)
                .where(searchByLikeGoe(likeCond), searchByViewGoe(viewCond), searchByFoodNameContain(searchingFoodName))
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

    private BooleanExpression searchByViewGoe(Integer viewCond) {
        if (viewCond == null) {
            return null;
        }
        return recipe.viewCount.goe(viewCond);
    }

    @Override
    public List<Recipe> Top10RecipeLike() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.likeCount.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10RecipeView() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.viewCount.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10RecipeRatingScore() {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.ratingPeople.gt(0))
                .orderBy(recipe.ratingScore.desc(), recipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10Like() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.likeCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10View() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.viewCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<Recipe> Top10RatingScore() {
        return queryFactory
                .selectFrom(recipe)
                .orderBy(recipe.ratingScore.desc())
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
    public List<String> RecommendByAge(int inputAge) {
        return queryFactory
                .select(registerRecipe.foodName)
                .from(user)
                .join(registerRecipe)
                .on(user.userId.eq(registerRecipe.user.userId))
                .where(user.age.goe(inputAge * 10).and(user.age.lt(inputAge * 10)))
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
//    @Override
//    public List<Tuple> JoinTable() {
//        List<Tuple> fetch = queryFactory
//                .select(registerRecipe.foodName, registerRecipe.ingredient, registerRecipe.category, registerRecipe.likeCount.as("total_like"),
//                        recipe.foodName, recipe.ingredient, recipe.category, recipe.likeCount.as("total_like"))
//                .from(recipe, registerRecipe)
//                .orderBy(recipe.likeCount.desc())
//                .fetch();
//        return fetch;
//    }
}
