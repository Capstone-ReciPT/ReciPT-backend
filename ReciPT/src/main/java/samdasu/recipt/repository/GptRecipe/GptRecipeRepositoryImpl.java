package samdasu.recipt.repository.GptRecipe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.entity.QGptRecipe;

import java.util.List;

import static samdasu.recipt.entity.QGptRecipe.gptRecipe;

@Repository
@RequiredArgsConstructor
public class GptRecipeRepositoryImpl implements GptRecipeCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.add(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    @Override
    public void subGptLikeCount(GptRecipe selectedGptRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptLikeCount, gptRecipe.gptLikeCount.subtract(1))
                .where(gptRecipe.eq(selectedGptRecipe))
                .execute();
    }

    @Override
    public void addGptViewCount(GptRecipe selectedDbRecipe) {
        queryFactory.update(gptRecipe)
                .set(gptRecipe.gptViewCount, gptRecipe.gptViewCount.add(1))
                .where(gptRecipe.eq(selectedDbRecipe))
                .execute();
    }

    @Override
    public List<GptRecipe> findGptRecipeByContain(String searchingFoodName) {
        return queryFactory
                .selectFrom(gptRecipe)
                .where(gptRecipe.gptFoodName.contains(searchingFoodName))
                .fetch();
    }

    @Override
    public List<GptRecipe> Top10GptRecipeView() {
        return queryFactory
                .selectFrom(QGptRecipe.gptRecipe)
                .orderBy(QGptRecipe.gptRecipe.gptViewCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<GptRecipe> Top10GptRecipeLike() {
        return queryFactory
                .selectFrom(QGptRecipe.gptRecipe)
                .orderBy(QGptRecipe.gptRecipe.gptLikeCount.desc())
                .limit(10)
                .fetch();
    }

    /**
     * Gpt 좋아요 1위 조회
     */
    @Override
    public GptRecipe Top1GptRecipeLike() {
        return queryFactory
                .selectFrom(gptRecipe)
                .orderBy(gptRecipe.gptLikeCount.desc())
                .limit(1)
                .fetchOne();
    }

    /**
     * Gpt 조회 수 1위 조회
     */
    @Override
    public GptRecipe Top1GptRecipeViewCount() {
        return queryFactory
                .selectFrom(gptRecipe)
                .orderBy(gptRecipe.gptViewCount.desc())
                .limit(1)
                .fetchOne();
    }

    /**
     * Gpt 평점 1위
     */
    @Override
    public GptRecipe Top1GptRecipeRatingScore() {
        return queryFactory
                .selectFrom(gptRecipe)
                .orderBy(gptRecipe.gptRatingScore.desc())
                .limit(1)
                .fetchOne();
    }


    /**
     * 좋아요 사용자 입력 값 이상 조회
     */
    @Override
    public List<GptRecipe> SearchingGptRecipeLikeByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(gptRecipe)
                .where(gptRecipe.gptLikeCount.goe(inputNum))
                .fetch();
    }

    /**
     * 조회수 사용자 입력 값 이상 조회
     */
    @Override
    public List<GptRecipe> SearchingGptRecipeViewCountByInputNum(int inputNum) {
        return queryFactory
                .selectFrom(gptRecipe)
                .where(gptRecipe.gptViewCount.goe(inputNum))
                .fetch();
    }
}
