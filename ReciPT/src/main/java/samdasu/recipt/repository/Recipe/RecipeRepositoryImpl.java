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
    public List<Recipe> searchingRecipeLikeByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.likeCount.goe(inputNum))
                .fetch();
    }

    /**
     * 조회수 사용자 입력 값 이상 조회
     */
    @Override
    public List<Recipe> searchingRecipeViewCountByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.viewCount.goe(inputNum))
                .fetch();
    }

    @Override
    public List<Recipe> dynamicSearching() {
        return null;
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
