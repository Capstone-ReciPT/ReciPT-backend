package samdasu.recipt.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.domain.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.domain.entity.Recipe;
import samdasu.recipt.domain.repository.Recipe.RecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
        Recipe recipe = new Recipe("새우두부계란찜", "연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술), 고명, 시금치 10g(3줄기)", "찌기", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png", "1. 손질된 새우를 끓는 물에 데쳐 건진다. 2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다. 3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.", "http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png", 0L, 0, 5.0, 1);
        em.persist(recipe);
        
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.createReviewRequestDto("계란찜 맛있다.", 3.0);

        //when
        recipeService.updateRatingScore(recipe.getRecipeId(), reviewRequestDto);

        //then
        assertThat(recipe.getRatingPeople()).isEqualTo(2);
        assertThat(recipe.getRatingScore()).isEqualTo(4.0);
    }
    
    @DisplayName("카테고리 검색")
    @Test
    void findByCategory() {
        //given
        List<Recipe> recipes = recipeService.findByCategory("채소");
        
        //when
        for (Recipe recipe : recipes) {
            log.info("recipe.getFoodName() = {}", recipe.getFoodName());
        }
        //then
        assertThat(recipes.size()).isEqualTo(8);
    }

    @DisplayName("조회수 증가")
    @Test
    void increaseViewCount() {
        //given
        Recipe recipe = recipeService.findByFoodName("새우두부계란찜");
        
        //when
        recipeService.increaseViewCount(recipe.getRecipeId());
        
        //then
        assertThat(recipe.getViewCount()).isEqualTo(1);
    }
    
    @DisplayName("좋아요 Top 10")
    @Test
    void findTop10Like() {
        //given

        //when
        List<Recipe> top10Like = recipeService.findTop10Like();

        //then
        assertThat(top10Like.size()).isEqualTo(10);
    }

    @DisplayName("조회수 Top 10")
    @Test
    void findTop10View() {
        //given

        //when
        List<Recipe> top10View = recipeService.findTop10View();

        //then
        assertThat(top10View.size()).isEqualTo(10);
    }

    @DisplayName("평점 Top 10")
    @Test
    void findTop10RatingScore() {
        //given

        //when
        List<Recipe> top10RatingScore = recipeService.findTop10RatingScore();

        //then
        assertThat(top10RatingScore.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        Recipe findRecipe = recipeService.findByFoodName("새우두부계란찜");

        //then
        assertThat(findRecipe.getFoodName()).isEqualTo("새우두부계란찜");
    }

    @Test
    public void 검색조건_동적쿼리() throws Exception {
        //given

        //when
        List<Recipe> findByFoodName = recipeService.searchDynamicSearching("계란찜", 0, 0L);

        //then
        assertThat(findByFoodName.size()).isEqualTo(1);
    }

    @Test
    public void 레시피_전체조회() throws Exception {
        //given
        
        //when
        List<Recipe> recipes = recipeService.findRecipes();

        //then
        assertThat(recipes.size()).isEqualTo(50);
    }
}