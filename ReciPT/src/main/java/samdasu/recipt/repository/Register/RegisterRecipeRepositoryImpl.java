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


//    @Override
//    public List<GptRecipe> Top10GptRecipeView() {
//        return queryFactory
//                .selectFrom(QGptRecipe.gptRecipe)
//                .orderBy(QGptRecipe.gptRecipe.gptViewCount.desc())
//                .limit(10)
//                .fetch();
//    }
//
//    @Override
//    public List<GptRecipe> Top10GptRecipeLike() {
//        return queryFactory
//                .selectFrom(QGptRecipe.gptRecipe)
//                .orderBy(QGptRecipe.gptRecipe.gptLikeCount.desc())
//                .limit(10)
//                .fetch();
//    }
//
//    /**
//     * Gpt 좋아요 1위 조회
//     */
//    @Override
//    public GptRecipe Top1GptRecipeLike() {
//        return queryFactory
//                .selectFrom(gptRecipe)
//                .orderBy(gptRecipe.gptLikeCount.desc())
//                .limit(1)
//                .fetchOne();
//    }
//
//    /**
//     * Gpt 조회 수 1위 조회
//     */
//    @Override
//    public GptRecipe Top1GptRecipeViewCount() {
//        return queryFactory
//                .selectFrom(gptRecipe)
//                .orderBy(gptRecipe.gptViewCount.desc())
//                .limit(1)
//                .fetchOne();
//    }
//
//    /**
//     * Gpt 평점 1위
//     */
//    @Override
//    public GptRecipe Top1GptRecipeRatingScore() {
//        return queryFactory
//                .selectFrom(gptRecipe)
//                .orderBy(gptRecipe.gptRatingScore.desc())
//                .limit(1)
//                .fetchOne();
//    }
}
