package samdasu.recipt.repository.Register;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.RegisterRecipe;

import java.time.LocalDateTime;
import java.util.List;

import static samdasu.recipt.entity.QRegisterRecipe.registerRecipe;
import static samdasu.recipt.entity.QUser.user;

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
    public List<RegisterRecipe> Top10Like() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.likeCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10View() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.viewCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RatingScore() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.ratingScore.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RecentRegister() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.createDate.desc())
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
                .update(registerRecipe)
                .set(registerRecipe.viewCount, 0L)
                .where(registerRecipe.createDate.loe(yesterday))
                .execute();
    }
}
