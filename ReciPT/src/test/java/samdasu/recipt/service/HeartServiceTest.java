package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Heart.RecipeHeartDto;
import samdasu.recipt.controller.dto.Heart.RegisterHeartDto;
import samdasu.recipt.controller.dto.Heart.ReviewHeartDto;
import samdasu.recipt.entity.*;
import samdasu.recipt.repository.HeartRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeartServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    HeartRepository heartRepository;
    @Autowired
    HeartService heartService;

    @Test
    @Rollback(value = false)
    public void Recipe_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();
//         Review.createRecipeReview("새우두부계란찜 후기", 0, 3.0, user, recipe);
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(user.getUserId(), recipe.getRecipeId());

        //when
        heartService.insertRecipeHeart(recipeHeartDto);

        em.flush();
        em.clear();
        //then
        assertThat(recipe.getLikeCount()).isEqualTo(1);
    }

    @Test
    @Rollback(value = false)
    public void Recipe_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();

        createHeart(user, recipe); //Heart 저장

        //when
        RecipeHeartDto recipeHeartDto = RecipeHeartDto.createRecipeHeartDto(user.getUserId(), recipe.getRecipeId());
        heartService.deleteRecipeHeart(recipeHeartDto);

        //then
        assertThat(recipe.getLikeCount()).isEqualTo(-1);
    }

    @Test
    public void RegisterRecipe_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        RegisterRecipe registerRecipe = createRegisterRecipe(user);

        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId());

        //when
        heartService.insertRegisterRecipeHeart(registerHeartDto);

        //then
        assertThat(registerRecipe.getLikeCount()).isEqualTo(1);
    }

    @Test
    public void RegisterRecipe_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        RegisterRecipe registerRecipe = createRegisterRecipe(user);

        createHeart(user, registerRecipe); //Heart 저장

        //when
        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId());
        heartService.deleteRegisterRecipeHeart(registerHeartDto);

        //then
        assertThat(registerRecipe.getLikeCount()).isEqualTo(-1);
    }

    @Test
    @Rollback(value = false)
    public void Review_레시피_좋아요() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe();

        Review recipeReview = createRecipeReview(user, recipe);

        ReviewHeartDto recipeHeartDto = ReviewHeartDto.createRecipeHeartDto(user.getUserId(), recipeReview.getReviewId());

        //when
        heartService.insertReviewHeart(recipeHeartDto);

        //then
        assertThat(recipeReview.getLikeCount()).isEqualTo(1);
    }

    @Test
    public void Review_레시피_좋아요_취소() throws Exception {
        //given
        User user = createUser();
        RegisterRecipe registerRecipe = createRegisterRecipe(user);

        createHeart(user, registerRecipe); //Heart 저장

        //when
        RegisterHeartDto registerHeartDto = RegisterHeartDto.createRegisterHeartDto(user.getUserId(), registerRecipe.getRegisterId());
        heartService.deleteRegisterRecipeHeart(registerHeartDto);

        //then
        assertThat(registerRecipe.getLikeCount()).isEqualTo(-1);
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234", 10);
        em.persist(user);

        return user;
    }

    private Recipe createRecipe() {
        Recipe recipe = Recipe.createRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0L, 0, 0.0, 0);
        em.persist(recipe);

        return recipe;
    }


    private RegisterRecipe createRegisterRecipe(User user) {
        RegisterRecipe registerRecipe = RegisterRecipe.createRegisterRecipe("만두", null, "만두 먹기!", "만두는 진리", "기타", "고기피, 만두피", "1.만두 빚기 2.굽기 3.먹기",
                0L, 0, 0.0, 0, user, null, null);

        em.persist(registerRecipe);

        Review review = Review.createRegisterReview("만두 후기", 0, 4.5, user, registerRecipe);
        em.persist(review);

        return registerRecipe;
    }

    private Review createRecipeReview(User user, Recipe recipe) {
        Review review = Review.createRecipeReview("새우두부계란찜 후기", 0, 3.0, user, recipe);
        em.persist(review);

        return review;
    }

    private Heart createHeart(User user, Recipe recipe) {
        Heart heart = Heart.createRecipeHeart(user, recipe);
        em.persist(heart);

        return heart;
    }

    private Heart createHeart(User user, RegisterRecipe registerRecipe) {
        Heart heart = Heart.createRegiterRecipeHeart(user, registerRecipe);
        em.persist(heart);

        return heart;
    }

    private Heart createHeart(User user, Review review) {
        Heart heart = Heart.createReviewHeart(user, review);
        em.persist(heart);

        return heart;
    }
}