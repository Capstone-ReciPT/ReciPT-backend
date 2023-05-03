package samdasu.recipt.service;

import com.querydsl.core.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.Db.DbResponseDto;
import samdasu.recipt.controller.dto.Review.ReviewRequestDto;
import samdasu.recipt.entity.DbRecipe;
import samdasu.recipt.repository.DbRecipe.DbRecipeRepository;
import samdasu.recipt.repository.GptRecipe.GptRecipeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbRecipeServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    DbRecipeRepository dbRecipeRepository;
    @Autowired
    DbRecipeService dbRecipeService;
    @Autowired
    GptRecipeRepository gptRecipeRepository;

    @Test
    public void 평점_갱신() throws Exception {
        //given
        DbRecipe recipe = createRecipe();
        ReviewRequestDto dbReviewRequestDto = ReviewRequestDto.createDbReviewRequestDto("testerA", "리뷰 제목1", "계란말이 맛있다.", 3.5, recipe, null);

        //when
        dbRecipeService.dbUpdateRatingScore(recipe.getDbRecipeId(), dbReviewRequestDto);

        //then
        assertThat(recipe.getDbRatingPeople()).isEqualTo(2);
        assertThat(recipe.getDbRatingScore()).isEqualTo(8.5);
    }

    @Test
    public void 평점_계산() throws Exception {
        //given
        DbRecipe recipe = createRecipe();
        ReviewRequestDto dbReviewRequestDto = ReviewRequestDto.createDbReviewRequestDto("testerA", "리뷰 제목1", "계란말이 맛있다.", 3.5, recipe, null);
        dbRecipeService.dbUpdateRatingScore(recipe.getDbRecipeId(), dbReviewRequestDto);

        //when
        DbResponseDto dbResponseDto = dbRecipeService.calcDbRatingScore(recipe);

        //then
        assertThat(dbResponseDto.getDbRatingPeople()).isEqualTo(2);
        assertThat(dbResponseDto.getDbRatingResult()).isEqualTo(4.25);
    }

    @Test
    @Rollback(value = false)
    public void DB_조회수_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 top10 ###############");
        List<DbRecipe> top10ViewCount = dbRecipeService.findTop10ViewCount();
        //then
        for (DbRecipe recipe : top10ViewCount) {
            System.out.println("dbRecipe.getDbViewCount() = " + recipe.getDbViewCount());
        }
    }

    @Test
    public void DB_좋아요_탑_10() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top10 ###############");
        List<DbRecipe> top10LikeCount = dbRecipeService.findTop10LikeCount();
        //then
        for (DbRecipe recipe : top10LikeCount) {
            System.out.println("dbRecipe.getDbLikeCount() = " + recipe.getDbLikeCount());
        }
    }

    @Test
    public void DB_레시피_음식이름_단건조회() throws Exception {
        //given

        //when
        DbRecipe dbRecipe = dbRecipeService.findByFoodName("새우두부계란찜");

        //then
        assertThat(dbRecipe.getDbFoodName()).isEqualTo("새우두부계란찜");
    }

    @Test
    public void DB_레시피_음식이름포함_조회성공() throws Exception {
        //given

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findDbRecipeByContain("해장국");

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(1);
    }

    @Test
    public void DB_레시피_음식이름포함_조회실패() throws Exception {
        //given

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findDbRecipeByContain("고기");

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(0);
    }

    @Test
    public void 좋아요_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 좋아요 수 top1 ###############");
        DbRecipe top1DbRecipeLike = dbRecipeService.findTop1DbRecipeLike();

        //then
        System.out.println("top1DbRecipeLike.getDbFoodName() = " + top1DbRecipeLike.getDbFoodName());
        System.out.println("top1DbRecipeLike.getDbLikeCount() = " + top1DbRecipeLike.getDbLikeCount());
    }

    @Test
    public void 조회수_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 조회수 수 top1 ###############");
        DbRecipe top1DbRecipeViewCount = dbRecipeService.findTop1DbRecipeViewCount();

        //then
        System.out.println("top1DbRecipeViewCount.getDbFoodName() = " + top1DbRecipeViewCount.getDbFoodName());
        System.out.println("top1DbRecipeViewCount.getDbViewCount() = " + top1DbRecipeViewCount.getDbViewCount());
    }

    @Test
    public void 평점_탑1() throws Exception {
        //given

        //when
        System.out.println("############### 평점 top1 ###############");
        DbRecipe top1DbRecipeRatingScore = dbRecipeService.findTop1DbRecipeRatingScore();

        //then
        System.out.println("top1DbRecipeRatingScore.getDbFoodName() = " + top1DbRecipeRatingScore.getDbFoodName());
        System.out.println("top1DbRecipeRatingScore.getDbRatingScore() = " + top1DbRecipeRatingScore.getDbRatingScore());
    }

    @Test
    public void 사용자_입력_값보다_높은_좋아요_찾기() throws Exception {
        //given
        int inputNum = 3;
        //when
        List<DbRecipe> searchingDbRecipeLikeByInputNum = dbRecipeService.findSearchingDbRecipeLikeByInputNum(inputNum);

        //then
        for (DbRecipe dbRecipe : searchingDbRecipeLikeByInputNum) {
            System.out.println("dbRecipe.getDbFoodName() = " + dbRecipe.getDbFoodName());
            System.out.println("dbRecipe.getDbLikeCount() = " + dbRecipe.getDbLikeCount());
        }

    }

    @Test
    public void 사용자_입력_값보다_높은_조회수_찾기() throws Exception {
        //given
        int inputNum = 3;
        //when
        List<DbRecipe> searchingDbRecipeViewCountByInputNum = dbRecipeService.findSearchingDbRecipeViewCountByInputNum(inputNum);

        //then
        for (DbRecipe dbRecipe : searchingDbRecipeViewCountByInputNum) {
            System.out.println("dbRecipe.getDbFoodName() = " + dbRecipe.getDbFoodName());
            System.out.println("dbRecipe.getDbViewCount() = " + dbRecipe.getDbViewCount());
        }
    }

    public List<DbRecipe> findAll() {
        return dbRecipeRepository.findAll();
    }

    @Test
    public void DB_레시피_전체조회() throws Exception {
        //given

        //when
        List<DbRecipe> dbRecipes = dbRecipeService.findDbRecipes();

        //then
        Assertions.assertThat(dbRecipes.size()).isEqualTo(15);
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

        List<Tuple> hearts = dbRecipeRepository.Top10AllRecipeLike();
        for (Tuple heart : hearts) {
            System.out.println("heart = " + heart);
        }
    }

    private DbRecipe createRecipe() {
        DbRecipe dbRecipe = DbRecipe.createDbRecipe("방울토마토 소박이", "방울토마토 150g(5개), 양파 10g(3×1cm), 부추 10g(5줄기), 고춧가루 4g(1작은술), 멸치액젓 3g(2/3작은술), 다진 마늘 2.5g(1/2쪽), 매실액 2g(1/3작은술), 설탕 2g(1/3작은술), 물 2ml(1/3작은술), 통깨 약간",
                "기타", " http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00031_2.png", "1. 물기를 빼고 2cm 정도의 크기로 썰은 부추와 양파를 양념장에 섞어 양념속을 만든다. 2. 깨끗이 씻은 방울토마토는 꼭지를 떼고 윗부분에 칼로 십자모양으로 칼집을 낸다. 3. 칼집을 낸 방울토마토에 양념속을 사이사이에 넣어 버무린다.",
                "http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_1.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_4.png, http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00031_5.png",
                0, 0L, 5.0, 1, null);
        em.persist(dbRecipe);

        return dbRecipe;
    }
}