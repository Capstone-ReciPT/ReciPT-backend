package samdasu.recipt.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.controller.dto.Register.RegisterRequestDto;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterRecipeServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    RegisterRecipeService registerRecipeService;


    @Test
    public void 평점_갱신() throws Exception {
        //given
        User user = createUser10();
        Gpt gpt = createGpt(user);
        RegisterRecipe recipe = createRecipe(user, gpt);
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("만두 맛있다.", 4.0);

        //when
        registerRecipeService.updateRatingScore(recipe.getRegisterId(), reviewRequestDto);

        //then
        assertThat(recipe.getRatingPeople()).isEqualTo(2);
        assertThat(recipe.getRatingScore()).isEqualTo(4.5);
    }

    @Test
    public void 레시피_저장() throws Exception {
        //given
        User user = createUser10();
        Gpt gpt = createGpt(user);

        //when
        RegisterRequestDto registerRequestDto = RegisterRequestDto.createRegisterRequestDto("제목", "1줄평", "카테고리", null, null);
        Long saveRegisterRecipeId = registerRecipeService.registerRecipeSave(user.getUserId(), gpt.getGptId(),null,null,  registerRequestDto);

        RegisterRecipe findById = registerRecipeService.findById(saveRegisterRecipeId);

        //then
        assertThat(findById.getTitle()).isEqualTo("제목");
    }

    @Test
    public void 레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        RegisterRecipe registerRecipe = registerRecipeService.findByFoodName("만두");

        //then
        assertThat(registerRecipe.getFoodName()).isEqualTo("만두");
    }

    @Test
    public void 검색조건_동적쿼리() throws Exception {
        //given

        //when
        List<RegisterRecipe> findRegisterRecipes = registerRecipeService.searchDynamicSearching("만두", 0, 0L);

        //then
        assertThat(findRegisterRecipes.size()).isEqualTo(1);
    }


    @Test
    public void 레시피_전체조회() throws Exception {
        //given

        //when
        List<RegisterRecipe> registerRecipes = registerRecipeService.findRegisterRecipes();

        //then
        assertThat(registerRecipes.size()).isEqualTo(3);
    }

    @Test
    public void 레시피_좋아요_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeLike = registerRecipeService.findTop10Like();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeLike) {
            log.info("registerRecipe.getLikeCount() = " + registerRecipe.getLikeCount());
        }
    }

    @Test
    public void 레시피_조회수_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeView = registerRecipeService.findTop10View();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeView) {
            log.info("registerRecipe.getLikeCount() = " + registerRecipe.getLikeCount());
        }
    }


    @Test
    public void 레시피_평점순_Top10() throws Exception {
        //given

        //when
        List<RegisterRecipe> top10RegisterRecipeRatingScore = registerRecipeService.findTop10RatingScore();

        //then
        for (RegisterRecipe registerRecipe : top10RegisterRecipeRatingScore) {
            log.info("registerRecipe.getLikeCount() = {}", registerRecipe.getLikeCount());
        }
    }

    @Test
//    @Rollback(value = false)
    public void 연령별_추천() throws Exception {
        //given
        User userAge10 = createUser10();
        User userAge20 = createUser20();
        Gpt gpt = createGpt(userAge10);
        testRecommendByAge1(userAge10, gpt);
        testRecommendByAge2(userAge20, gpt);

        List<String> recommendByAge = registerRecipeService.RecommendByAge(15);
        log.info("recommendByAge.size() = {}", recommendByAge.size());
        //when
        for (String recommend : recommendByAge) {
            log.info("recommend = {}", recommend);
        }

        //then
    }

    private Gpt createGpt(User user) {
        Gpt gpt = Gpt.createGpt("만두", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기", user);
        em.persist(gpt);

        return gpt;
    }

    private User createUser10() {
        User user = User.createUser("tester1", "testId", "test1234", 10, null);
        em.persist(user);

        return user;
    }

    private User createUser20() {
        User user = User.createUser("tester1", "testId", "test1234", 20, null);
        em.persist(user);

        return user;
    }

    private RegisterRecipe createRecipe(User user, Gpt gpt) {
        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe(gpt.getFoodName(), "만두 먹기", "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                0L, 0, 5.0, 1, null, null, user, gpt );
        em.persist(registerRecipe);

        return registerRecipe;
    }

    private void testRecommendByAge1(User user, Gpt gpt) {
        for (int i = 0; i < 15; i++) {
            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe("10대가 좋아하는 음식" + i,  "10대가 좋아하는 음식", "음료수랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                    (long) 20 + i, 20 + i, 5.0, 1, null, null,user, gpt);
            em.persist(registerRecipe);
        }
    }

    private void testRecommendByAge2(User user, Gpt gpt) {
        for (int i = 0; i < 15; i++) {
            RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe("20대가 좋아하는 음식" + i, "20대가 좋아하는 음식", "우유랑 먹으면 맛있어요.", "기타", gpt.getIngredient(), gpt.getContext(),
                    (long) 50 + i, 50 + i, 4.0, 1, null,null, user, gpt);
            em.persist(registerRecipe);
        }
    }
}