package samdasu.recipt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.Recipe;
import samdasu.recipt.entity.User;
import samdasu.recipt.repository.Recipe.RecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    RecipeRepository dbRecipeRepository;
    @Autowired
    RecipeService recipeService;

    @Test
    public void 평점_갱신() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe(user);
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("계란찜 맛있다.", 3.0);

        //when
        recipeService.updateRatingScore(recipe.getRecipeId(), reviewRequestDto);

        //then
        assertThat(recipe.getRatingPeople()).isEqualTo(2);
        assertThat(recipe.getRatingScore()).isEqualTo(4.0);
    }

    @Test
    public void 레시피_음식이름_단건조회() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe(user);

        //when
        Recipe findRecipe = recipeService.findByFoodName("새우두부계란찜");

        //then
        assertThat(findRecipe.getFoodName()).isEqualTo("새우두부계란찜");
    }

    @Test
    public void 레시피_음식이름포함_조회성공() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe(user);

        //when
        List<Recipe> findByFoodName = recipeService.findRecipeByContain("계란찜");

        //then
        assertThat(findByFoodName.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_음식이름포함_조회실패() throws Exception {
        //given
        User user = createUser();
        Recipe recipe = createRecipe(user);

        //when
        List<Recipe> findByFoodName = recipeService.findRecipeByContain("고기");

        //then
        assertThat(findByFoodName.size()).isEqualTo(0);
    }


    @Test
    public void 레시피_전체조회() throws Exception {
        //given

        //when
        List<Recipe> recipes = recipeService.findRecipes();

        //then
        assertThat(recipes.size()).isEqualTo(4);
    }

    @Test
    @Rollback(value = false)
    public void DB_GPT_조회수_탑10() throws Exception {
        //given

        //when
//        Page<RecipeProjection> result = dbRecipeRepository.Top10AllRecipeLike(PageRequest.of(0, 10));
//        List<RecipeProjection> content = result.getContent();
//        for (RecipeProjection recipeProjection : content) {
//            System.out.println("recipeProjection = " + recipeProjection.getFoodName());
//            System.out.println("recipeProjection.getIngredient() = " + recipeProjection.getIngredient());
//        }
        //then

//        List<Tuple> hearts = dbRecipeRepository.Top10AllRecipeLike();
//        for (Tuple heart : hearts) {
//            System.out.println("heart = " + heart);
//        }
    }

    private User createUser() {
        User user = User.createUser("tester1", "testId", "test1234", 10);
        em.persist(user);

        return user;
    }

    private Recipe createRecipe(User user) {
        Recipe recipe = Recipe.createRecipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)",
                "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png",
                0L, 0, 5.0, 1);
        em.persist(recipe);

        return recipe;
    }
}