package samdasu.recipt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Gpt.GptRequestDto;
import samdasu.recipt.controller.dto.Gpt.GptResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Allergy;
import samdasu.recipt.entity.GptRecipe;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GptRecipeServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    GptRecipeService gptRecipeService;
    @Autowired
    GptRecipeRepository gptRecipeRepository;

    @Test
    public void 평점_갱신() throws Exception {
        //given
        GptRecipe recipe = createRecipe();
        ReviewRequestDto gptReviewRequestDto = ReviewRequestDto.createReviewRequestDto("testerA", "리뷰 제목1", "계란말이 맛있다.", 3.5);

        //when
        gptRecipeService.gptUpdateRatingScore(recipe.getGptRecipeId(), gptReviewRequestDto);

        //then
        assertThat(recipe.getGptRatingPeople()).isEqualTo(2);
        assertThat(recipe.getGptRatingScore()).isEqualTo(8.5);
    }

    @Test
    public void 평점_계산() throws Exception {
        //given
        GptRecipe recipe = createRecipe();
        ReviewRequestDto gptReviewRequestDto = ReviewRequestDto.createReviewRequestDto("testerA", "리뷰 제목1", "계란말이 맛있다.", 5.0);
        gptRecipeService.gptUpdateRatingScore(recipe.getGptRecipeId(), gptReviewRequestDto);

        //when
        GptResponseDto gptResponseDto = gptRecipeService.calcGptRatingScore(recipe);

        //then
        assertThat(gptResponseDto.getGptRatingPeople()).isEqualTo(2);
        assertThat(gptResponseDto.getGptRatingResult()).isEqualTo(4.25);
    }

    @Test
    public void Gpt_레시피_저장() throws Exception {
        //given
        GptRecipe recipe = createRecipe();
        //when
        GptRequestDto gptDto = GptRequestDto.createGptDto(recipe);
        gptRecipeService.gptSave(gptDto);
        //then
        assertThat(gptDto.getGptFoodName()).isEqualTo(recipe.getGptFoodName());
    }

    @Test
    public void Gpt_조회수_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<GptRecipe> top10ViewCount = gptRecipeService.findTop10ViewCount();
        //then
        for (GptRecipe recipe : top10ViewCount) {
            System.out.println("gptRecipe.getGptViewCount() = " + recipe.getGptViewCount());
        }
    }

    @Test
    public void Gpt_좋아요_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top10 ###############");
        List<GptRecipe> top10LikeCount = gptRecipeService.findTop10LikeCount();
        //then
        for (GptRecipe recipe : top10LikeCount) {
            System.out.println("recipe.getGptLikeCount() = " + recipe.getGptLikeCount());
        }
    }

    @Test
    public void Gpt_레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        GptRequestDto result = gptRecipeService.findByFoodName("계란후라이");

        //then
        assertThat(result.getGptFoodName()).isEqualTo("계란후라이");
    }

    @Test
    public void Gpt_레시피_음식이름포함_조회성공() throws Exception {
        //given

        //when
        List<GptRecipe> gptRecipes = gptRecipeService.findGptRecipeByContain("계란후라이");

        //then
        Assertions.assertThat(gptRecipes.size()).isEqualTo(1);
    }

    @Test
    public void Gpt_레시피_음식이름포함_조회실패() throws Exception {
        //given

        //when
        List<GptRecipe> gptRecipes = gptRecipeService.findGptRecipeByContain("고기");

        //then
        Assertions.assertThat(gptRecipes.size()).isEqualTo(0);
    }


    @Test
    public void 좋아요_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top1 ###############");
        GptRecipe top1DbRecipeLike = gptRecipeService.findTop1GptRecipeLike();

        //then
        System.out.println("top1DbRecipeLike.getGptFoodName() = " + top1DbRecipeLike.getGptFoodName());
        System.out.println("top1DbRecipeLike.getGptLikeCount() = " + top1DbRecipeLike.getGptLikeCount());
    }

    @Test
    public void 조회수_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 수 top1 ###############");
        GptRecipe top1DbRecipeViewCount = gptRecipeService.findTop1GptRecipeViewCount();

        //then
        System.out.println("top1DbRecipeViewCount.getGptFoodName() = " + top1DbRecipeViewCount.getGptFoodName());
        System.out.println("top1DbRecipeViewCount.getGptViewCount() = " + top1DbRecipeViewCount.getGptViewCount());
    }

    @Test
    public void 평점_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 평점 top1 ###############");
        GptRecipe top1GptRecipeRatingScore = gptRecipeService.findTop1GptRecipeRatingScore();

        //then
        System.out.println("top1GptRecipeRatingScore.getGptFoodName() = " + top1GptRecipeRatingScore.getGptFoodName());
        System.out.println("top1GptRecipeRatingScore.getGptRatingScore() = " + top1GptRecipeRatingScore.getGptRatingScore());
    }

    @Test
    public void 사용자_입력_값보다_높은_좋아요_찾기() throws Exception {
        //given
        int inputNum = 3;
        //when
        List<GptRecipe> searchingDbRecipeLikeByInputNum = gptRecipeService.findSearchingGptRecipeLikeByInputNum(inputNum);

        //then
        for (GptRecipe gptRecipe : searchingDbRecipeLikeByInputNum) {
            System.out.println("gptRecipe.getGptFoodName() = " + gptRecipe.getGptFoodName());
            System.out.println("gptRecipe.getGptLikeCount() = " + gptRecipe.getGptLikeCount());
        }

    }

    @Test
    public void 사용자_입력_값보다_높은_조회수_찾기() throws Exception {
        //given
        int inputNum = 3;
        //when
        List<GptRecipe> searchingDbRecipeViewCountByInputNum = gptRecipeService.findSearchingGptRecipeViewCountByInputNum(inputNum);

        //then
        for (GptRecipe gptRecipe : searchingDbRecipeViewCountByInputNum) {
            System.out.println("gptRecipe.getGptFoodName() = " + gptRecipe.getGptFoodName());
            System.out.println("gptRecipe.getGptViewCount() = " + gptRecipe.getGptViewCount());
        }
    }

    @Test
    public void Gpt_레시피_전체조회() throws Exception {
        //given

        //when
        List<GptRecipe> gptRecipes = gptRecipeRepository.findAll();

        //then
        assertThat(gptRecipes.size()).isEqualTo(16);
    }

    private GptRecipe createRecipe() {
        Allergy allergy = Allergy.createAllergy("갑각류", "새우");
        GptRecipe gptRecipe = GptRecipe.createGptRecipe("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", "음료수랑 먹으면 맛있어요.", 0, 0L, 5.0, 1, allergy);
        em.persist(gptRecipe);

        return gptRecipe;
    }
}