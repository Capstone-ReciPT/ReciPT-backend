package samdasu.recipt.repository.Register;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.RegisterRecipe;

import java.util.List;

import static samdasu.recipt.entity.QRegisterRecipe.registerRecipe;

@Repository
@RequiredArgsConstructor
public class RegisterRecipeRepositoryImpl implements RegisterCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addRegisterRecipeLikeCount(RegisterRecipe selectedRegisterRecipe) {
        queryFactory.update(registerRecipe)
                .set(registerRecipe.likeCount, registerRecipe.likeCount.add(1))
                .where(registerRecipe.eq(selectedRegisterRecipe))
                .execute();
    }

    @Override
    public void subRegisterRecipeLikeCount(RegisterRecipe selectedRegisterRecipe) {
        queryFactory.update(registerRecipe)
                .set(registerRecipe.likeCount, registerRecipe.likeCount.subtract(1))
                .where(registerRecipe.eq(selectedRegisterRecipe))
                .execute();
    }

    @Override
    public void addRegisterRecipeViewCount(RegisterRecipe selectedRegisterRecipe) {
        queryFactory.update(registerRecipe)
                .set(registerRecipe.viewCount, registerRecipe.viewCount.add(1))
                .where(registerRecipe.eq(selectedRegisterRecipe))
                .execute();
    }

    @Override
    public List<RegisterRecipe> dynamicSearching(int likeCond, int viewCond, String searchingFoodName) {
        return queryFactory
                .selectFrom(registerRecipe)
                .where(searchByLikeGoe(likeCond), searchByViewGoe(viewCond), searchByFoodNameContain(searchingFoodName))
                .fetch();
    }

    private BooleanExpression searchByFoodNameContain(String searchingFoodName) {
        if (searchingFoodName == null) {
            return null;
        }
        return registerRecipe.foodName.contains(searchingFoodName);
    }

    private BooleanExpression searchByLikeGoe(Integer likeCond) {
        if (likeCond == null) {
            return null;
        }
        return registerRecipe.viewCount.goe(likeCond);
    }

    private BooleanExpression searchByViewGoe(Integer viewCond) {
        if (viewCond == null) {
            return null;
        }
        return registerRecipe.viewCount.goe(viewCond);
    }


    @Override
    public List<RegisterRecipe> Top10RegisterRecipeLike() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.likeCount.desc(), registerRecipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RegisterRecipeView() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.viewCount.desc(), registerRecipe.createDate.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RegisterRecipeRatingScore() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.ratingScore.desc(), registerRecipe.createDate.desc())
                .limit(10)
                .fetch();
    }
}
