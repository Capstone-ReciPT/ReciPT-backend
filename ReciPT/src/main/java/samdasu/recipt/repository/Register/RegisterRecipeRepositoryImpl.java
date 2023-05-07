package samdasu.recipt.repository.Register;

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
    public List<RegisterRecipe> findRegisterRecipeByContain(String searchingFoodName) {
        return queryFactory
                .selectFrom(registerRecipe)
                .where(registerRecipe.foodName.contains(searchingFoodName))
                .fetch();
    }
    

    /**
     * 좋아요 사용자 입력 값 이상 조회
     */
    @Override
    public List<RegisterRecipe> SearchingRegisterRecipeLikeByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(registerRecipe)
                .where(registerRecipe.likeCount.goe(inputNum))
                .fetch();
    }

    /**
     * 조회수 사용자 입력 값 이상 조회
     */
    @Override
    public List<RegisterRecipe> SearchingRegisterRecipeViewCountByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(registerRecipe)
                .where(registerRecipe.viewCount.goe(inputNum))
                .fetch();
    }


    @Override
    public List<RegisterRecipe> Top10RegisterRecipeLike() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.likeCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RegisterRecipeView() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.viewCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<RegisterRecipe> Top10RegisterRecipeRatingScore() {
        return queryFactory
                .selectFrom(registerRecipe)
                .orderBy(registerRecipe.ratingScore.desc())
                .limit(10)
                .fetch();
    }
}
